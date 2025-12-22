package com.blackgoose.fare_the_well;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.blackgoose.fare_the_well.Adapters.PreviewImagesAdapter;
import com.blackgoose.fare_the_well.Adapters.ProgramAdapter;
import com.blackgoose.fare_the_well.Models.ProgramModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreviewActivity extends AppCompatActivity {

    private ImageView mainImageView;
    private ViewPager2 viewPager;
    private RecyclerView programsRv;
    private Button uploadBtn;
    private ProgressDialog progressDialog;

    private String mainImageUri = "";
    private ArrayList<String> galleryImages;
    private ArrayList<ProgramModel> programs;

    private String firstName, secondName, lastName, burialLocation, birthYear, passingYear,
            eulogyText, authorName, authorPhone;

    private String mainImageDownloadUrl = "";

    // NEW FIELDS
    private String userId;
    private String mpesaReceipt;  // optional if you add real receipt later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Get current user ID
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Bind UI
        mainImageView = findViewById(R.id.deceased_image);
        viewPager = findViewById(R.id.viewPager);
        programsRv = findViewById(R.id.reviewProgramsRv);
        uploadBtn = findViewById(R.id.uploadBtn);

        TextView tvFirst = findViewById(R.id.deceased_Fname);
        TextView tvSecond = findViewById(R.id.deceased_Sname);
        TextView tvLast = findViewById(R.id.deceased_Lname);
        TextView tvBirth = findViewById(R.id.deceased_dob);
        TextView tvPassing = findViewById(R.id.deceased_passing);
        TextView tvBurial = findViewById(R.id.burial_location);
        TextView tvEulogy = findViewById(R.id.life_biography);
        TextView tvAuthorName = findViewById(R.id.author_name);
        TextView tvAuthorPhone = findViewById(R.id.author_phone);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.setCancelable(false);

        // Get Intent data
        mainImageUri = getIntent().getStringExtra("mainImageUri");
        galleryImages = getIntent().getStringArrayListExtra("galleryImages");
        programs = (ArrayList<ProgramModel>) getIntent().getSerializableExtra("programs");

        firstName = getIntent().getStringExtra("firstName");
        secondName = getIntent().getStringExtra("secondName");
        lastName = getIntent().getStringExtra("lastName");
        burialLocation = getIntent().getStringExtra("burialLocation");
        birthYear = getIntent().getStringExtra("birthYear");
        passingYear = getIntent().getStringExtra("passingYear");
        eulogyText = getIntent().getStringExtra("eulogyText");
        authorName = getIntent().getStringExtra("authorName");
        authorPhone = getIntent().getStringExtra("authorPhone");

        // OPTIONAL: if passed from previous activity
        mpesaReceipt = getIntent().getStringExtra("mpesaReceipt");
        if (mpesaReceipt == null) mpesaReceipt = "";

        // Set text in UI
        tvFirst.setText(firstName);
        tvSecond.setText(secondName);
        tvLast.setText(lastName);
        tvBirth.setText(birthYear);
        tvPassing.setText(passingYear);
        tvBurial.setText(burialLocation);
        tvEulogy.setText(eulogyText);
        tvAuthorName.setText(authorName);
        tvAuthorPhone.setText(authorPhone);

        // Main image
        if (mainImageUri != null) {
            Glide.with(this).load(Uri.parse(mainImageUri)).into(mainImageView);
        }

        // ViewPager gallery
        if (galleryImages != null && !galleryImages.isEmpty()) {
            PreviewImagesAdapter adapter = new PreviewImagesAdapter(PreviewActivity.this, galleryImages);
            viewPager.setAdapter(adapter);
        }

        // Programs list
        if (programs != null) {
            programsRv.setLayoutManager(new LinearLayoutManager(this));
            programsRv.setAdapter(new ProgramAdapter(programs));
        }

        uploadBtn.setOnClickListener(v -> uploadAllImages());
    }

    private void uploadAllImages() {

        if (mainImageUri == null) {
            Toast.makeText(this, "Main image missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (galleryImages == null || galleryImages.isEmpty()) {
            Toast.makeText(this, "Gallery images missing!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.show();

        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference("eulogyPictures");

        StorageReference mainRef = storageRef.child("main_" + System.currentTimeMillis() + ".jpg");

        mainRef.putFile(Uri.parse(mainImageUri))
                .continueWithTask(task -> mainRef.getDownloadUrl())
                .addOnSuccessListener(url -> {
                    mainImageDownloadUrl = url.toString();
                    uploadGalleryImages(storageRef);
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Main image upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void uploadGalleryImages(StorageReference storageRef) {

        ArrayList<String> uploadedGalleryUrls = new ArrayList<>();

        for (int i = 0; i < galleryImages.size(); i++) {

            Uri imageUri = Uri.parse(galleryImages.get(i));
            StorageReference gRef = storageRef.child("gallery_" + System.currentTimeMillis() + "_" + i + ".jpg");

            gRef.putFile(imageUri)
                    .continueWithTask(task -> gRef.getDownloadUrl())
                    .addOnSuccessListener(url -> {

                        uploadedGalleryUrls.add(url.toString());

                        if (uploadedGalleryUrls.size() == galleryImages.size()) {
                            saveToDatabase(uploadedGalleryUrls);
                        }

                    }).addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Gallery upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
    private void saveToDatabase(List<String> galleryUrls) {

        // Generate formatted date (short month name)
        long timestamp = System.currentTimeMillis();
        String dateCreated = new java.text.SimpleDateFormat("dd MMM yyyy")
                .format(new java.util.Date(timestamp));

        Map<String, Object> data = new HashMap<>();
        data.put("firstName", firstName);
        data.put("secondName", secondName);
        data.put("lastName", lastName);
        data.put("birthYear", birthYear);
        data.put("passingYear", passingYear);
        data.put("burialLocation", burialLocation);
        data.put("eulogyText", eulogyText);
        data.put("authorName", authorName);
        data.put("authorPhone", authorPhone);

        data.put("mainImageUrl", mainImageDownloadUrl);
        data.put("galleryImages", galleryUrls);
        data.put("funeralPrograms", programs);
        data.put("status", "unpublished");

        // NEW FIELDS
        data.put("userId", userId);
        data.put("mpesaReceipt", mpesaReceipt);

        // DATE FIELDS
        data.put("dateCreated", dateCreated);
        data.put("timestamp", timestamp);

        FirebaseDatabase.getInstance()
                .getReference("Eulogies")
                .push()
                .setValue(data)
                .addOnSuccessListener(aVoid -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Eulogy Published Successfully", Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Failed to upload: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

}
