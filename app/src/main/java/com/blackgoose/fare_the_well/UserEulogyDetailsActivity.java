package com.blackgoose.fare_the_well;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.Adapters.EulogyAdapter;
import com.blackgoose.fare_the_well.Adapters.ProgramAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserEulogyDetailsActivity extends AppCompatActivity {

    private ImageView mainImageView;
    private RecyclerView programsRv;
    private GridView galleryGridView;

    private TextView deceasedFname, deceasedSname, deceasedLname, eulogyId;
    private TextView birthYear, passingYear, burialLocation, eulogyText;
    private TextView authorName, authorPhone, status;
    private ImageButton btnShare;

    private EulogyModel eulogy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_eulogy_detail);

        bindViews();

        eulogy = (EulogyModel) getIntent().getSerializableExtra("eulogy");
        if (eulogy == null) {
            Toast.makeText(this, "Eulogy data missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnShare.setOnClickListener(v -> shareEulogy());

        populateData();
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

        btnShare = findViewById(R.id.btnShare);
    }

    private void populateData() {
        // IDs and main image
        eulogyId.setText(eulogy.Eulogyid != null ? eulogy.Eulogyid : "N/A");

        if (eulogy.mainImageUrl != null && !eulogy.mainImageUrl.isEmpty()) {
            Glide.with(this)
                    .load(Uri.parse(eulogy.mainImageUrl))
                    .into(mainImageView);
        }

        // Names
        deceasedFname.setText(eulogy.firstName);
        deceasedSname.setText(eulogy.secondName);
        deceasedLname.setText(eulogy.lastName);

        // Dates
        birthYear.setText(eulogy.birthYear);
        passingYear.setText(eulogy.passingYear);

        // Burial location and eulogy text
        burialLocation.setText(eulogy.burialLocation);
        eulogyText.setText(eulogy.eulogyText);

        // Author info
        authorName.setText(eulogy.authorName);
        authorPhone.setText(eulogy.authorPhone);
        status.setText(eulogy.status);

        setupGallery();
        setupPrograms();
    }

    private void setupGallery() {
        ArrayList<String> galleryImages =
                eulogy.galleryImages != null ? eulogy.galleryImages : new ArrayList<>();

        // Use your existing adapter
        EulogyAdapter adapter = new EulogyAdapter(
                this,
                galleryImages,
                position -> FullscreenActivity.open(this, galleryImages, position)
        );

        galleryGridView.setAdapter(adapter);
    }

    private void setupPrograms() {
        if (eulogy.funeralPrograms != null && !eulogy.funeralPrograms.isEmpty()) {
            programsRv.setLayoutManager(new LinearLayoutManager(this));
            programsRv.setAdapter(new ProgramAdapter(eulogy.funeralPrograms));
        }
    }

    private void shareEulogy() {
        if (eulogy.Eulogyid == null || eulogy.Eulogyid.isEmpty()) return;

        String shareUrl = "https://farewell.app/eulogy/" + eulogy.Eulogyid;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "In Loving Memory");
        intent.putExtra(Intent.EXTRA_TEXT, shareUrl);

        startActivity(Intent.createChooser(intent, "Share via"));
    }
}
