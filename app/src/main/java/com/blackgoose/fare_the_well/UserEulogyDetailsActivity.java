package com.blackgoose.fare_the_well;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.blackgoose.fare_the_well.Adapters.EulogyAdapter;
import com.blackgoose.fare_the_well.Adapters.ProgramAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class UserEulogyDetailsActivity extends AppCompatActivity {

    private ImageView mainImageView;
    private RecyclerView programsRv;
    private GridView galleryGridView;

    private TextView deceasedFname, deceasedSname, deceasedLname, eulogyId;
    private TextView birthYear, passingYear, burialLocation, eulogyText;
    private TextView authorName, authorPhone, status, upload_date;

    private ImageButton btnShare;
    private Button btnPay;

    private ProgressDialog progressDialog;

    private EulogyModel eulogy;

    // USD PRICE
    private final double USD_AMOUNT = 25;

    // CONVERTED KES
    private int kesAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_eulogy_detail);

        bindViews();

        eulogy = (EulogyModel) getIntent().getSerializableExtra("eulogy");

        if (eulogy == null) {

            Toast.makeText(this,
                    "Eulogy data missing",
                    Toast.LENGTH_SHORT).show();

            finish();
            return;
        }

        btnShare.setOnClickListener(v -> shareEulogy());

        btnPay.setOnClickListener(v -> startSTKPush());

        populateData();

        fetchExchangeRate();
    }

    private void bindViews() {

        mainImageView = findViewById(R.id.deceased_image);

        programsRv = findViewById(R.id.reviewProgramsRv);

        galleryGridView = findViewById(R.id.galleryGridView);

        eulogyId = findViewById(R.id.id_eulogy);

        deceasedFname = findViewById(R.id.deceased_Fname);

        deceasedSname = findViewById(R.id.deceased_Sname);

        deceasedLname = findViewById(R.id.deceased_Lname);

        birthYear = findViewById(R.id.deceased_dob);

        passingYear = findViewById(R.id.deceased_passing);

        burialLocation = findViewById(R.id.burial_location);

        eulogyText = findViewById(R.id.life_biography);

        authorName = findViewById(R.id.author_name);

        authorPhone = findViewById(R.id.author_phone);

        status = findViewById(R.id.status);

        upload_date = findViewById(R.id.date_created);

        btnShare = findViewById(R.id.btnShare);

        btnPay = findViewById(R.id.payButton);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
    }

    private void populateData() {

        // ID
        eulogyId.setText(
                eulogy.Eulogyid != null ?
                        eulogy.Eulogyid : "N/A"
        );

        // MAIN IMAGE
        if (eulogy.mainImageUrl != null &&
                !eulogy.mainImageUrl.isEmpty()) {

            Glide.with(this)
                    .load(Uri.parse(eulogy.mainImageUrl))
                    .into(mainImageView);
        }

        // NAMES
        deceasedFname.setText(eulogy.firstName);
        deceasedSname.setText(eulogy.secondName);
        deceasedLname.setText(eulogy.lastName);

        // DATES
        birthYear.setText(eulogy.birthYear);
        passingYear.setText(eulogy.passingYear);

        // DETAILS
        burialLocation.setText(eulogy.burialLocation);

        eulogyText.setText(eulogy.eulogyText);

        // AUTHOR
        authorName.setText(eulogy.authorName);

        authorPhone.setText(eulogy.authorPhone);

        status.setText(eulogy.status);

        upload_date.setText(eulogy.getDateCreated());

        // HIDE BUTTON IF ALREADY PUBLISHED
        if ("published".equalsIgnoreCase(eulogy.status)) {

            btnPay.setEnabled(false);

            btnPay.setText("Already Published");
        }

        setupGallery();

        setupPrograms();
    }

    // LIVE USD -> KES CONVERSION
    private void fetchExchangeRate() {

        String url =
                "https://open.er-api.com/v6/latest/USD";

        RequestQueue queue =
                Volley.newRequestQueue(this);

        JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,

                        response -> {

                            try {

                                JSONObject rates =
                                        response.getJSONObject("rates");

                                double kesRate =
                                        rates.getDouble("KES");

                                double converted =
                                        USD_AMOUNT * kesRate;

                                kesAmount =
                                        (int) Math.round(converted);

                                DecimalFormat df =
                                        new DecimalFormat("#,###");

                                btnPay.setText(
                                        "Publish • Pay KES "
                                                + df.format(kesAmount)
                                );

                            } catch (JSONException e) {

                                kesAmount = 3250;

                                btnPay.setText(
                                        "Publish • Pay KES 3,250"
                                );
                            }

                        },

                        error -> {

                            kesAmount = 3250;

                            btnPay.setText(
                                    "Publish • Pay KES 3,250"
                            );

                        });

        queue.add(request);
    }

    // START MPESA STK PUSH
    private void startSTKPush() {

        String phone =
                FirebaseAuth.getInstance()
                        .getCurrentUser()
                        .getPhoneNumber();

        if (phone == null || phone.isEmpty()) {

            Toast.makeText(this,
                    "Phone number missing",
                    Toast.LENGTH_SHORT).show();

            return;
        }

        progressDialog.setMessage("Sending STK Push...");
        progressDialog.show();

        String url =
                "https://YOUR_CLOUD_FUNCTION_URL/initiateSTK";

        JSONObject body = new JSONObject();

        try {

            body.put("phone", phone);

            body.put("amount", kesAmount);

            body.put("eulogyId", eulogy.Eulogyid);

            body.put("accountReference", eulogy.Eulogyid);

            body.put("transactionDesc", "Farewell Eulogy Publish");

        } catch (JSONException e) {

            e.printStackTrace();
        }

        RequestQueue queue =
                Volley.newRequestQueue(this);

        JsonObjectRequest request =
                new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        body,

                        response -> {

                            progressDialog.dismiss();

                            Toast.makeText(this,
                                    "STK Push Sent",
                                    Toast.LENGTH_SHORT).show();

                            listenForPaymentStatus();
                        },

                        error -> {

                            progressDialog.dismiss();

                            Toast.makeText(this,
                                    "Payment failed",
                                    Toast.LENGTH_LONG).show();

                        });

        queue.add(request);
    }

    // LISTEN FOR PAYMENT SUCCESS
    private void listenForPaymentStatus() {

        progressDialog.setMessage(
                "Waiting for payment confirmation..."
        );

        progressDialog.show();

        FirebaseDatabase.getInstance()
                .getReference("Payments")
                .child(eulogy.Eulogyid)
                .addValueEventListener(new com.google.firebase.database.ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        if (!snapshot.exists()) return;

                        String paymentStatus =
                                snapshot.child("paymentStatus")
                                        .getValue(String.class);

                        String mpesaReceipt =
                                snapshot.child("mpesaReceipt")
                                        .getValue(String.class);

                        if ("success".equals(paymentStatus)) {

                            progressDialog.dismiss();

                            publishEulogy(mpesaReceipt);
                        }
                    }

                    @Override
                    public void onCancelled(com.google.firebase.database.DatabaseError error) {

                        progressDialog.dismiss();

                    }
                });
    }

    // UPDATE EULOGY AFTER SUCCESSFUL PAYMENT
    private void publishEulogy(String receipt) {

        FirebaseDatabase.getInstance()
                .getReference("Eulogies")
                .child(eulogy.Eulogyid)
                .child("status")
                .setValue("published");

        FirebaseDatabase.getInstance()
                .getReference("Eulogies")
                .child(eulogy.Eulogyid)
                .child("mpesaReceipt")
                .setValue(receipt)
                .addOnSuccessListener(unused -> {

                    status.setText("published");

                    btnPay.setEnabled(false);

                    btnPay.setText("Published");

                    Toast.makeText(
                            this,
                            "Eulogy Published Successfully",
                            Toast.LENGTH_LONG
                    ).show();

                });
    }

    private void setupGallery() {

        ArrayList<String> galleryImages =
                eulogy.galleryImages != null
                        ? eulogy.galleryImages
                        : new ArrayList<>();

        EulogyAdapter adapter =
                new EulogyAdapter(
                        this,
                        galleryImages,
                        position -> FullscreenActivity.open(
                                this,
                                galleryImages,
                                position
                        )
                );

        galleryGridView.setAdapter(adapter);
    }

    private void setupPrograms() {

        if (eulogy.funeralPrograms != null &&
                !eulogy.funeralPrograms.isEmpty()) {

            programsRv.setLayoutManager(
                    new LinearLayoutManager(this)
            );

            programsRv.setAdapter(
                    new ProgramAdapter(eulogy.funeralPrograms)
            );
        }
    }

    private void shareEulogy() {

        if (eulogy.Eulogyid == null ||
                eulogy.Eulogyid.isEmpty()) return;

        String shareUrl =
                "https://farewell.app/eulogy/"
                        + eulogy.Eulogyid;

        Intent intent =
                new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");

        intent.putExtra(
                Intent.EXTRA_SUBJECT,
                "In Loving Memory"
        );

        intent.putExtra(
                Intent.EXTRA_TEXT,
                shareUrl
        );

        startActivity(
                Intent.createChooser(intent, "Share via")
        );
    }
}