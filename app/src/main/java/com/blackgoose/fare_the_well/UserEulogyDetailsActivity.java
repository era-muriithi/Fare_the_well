package com.blackgoose.fare_the_well;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.blackgoose.fare_the_well.Adapters.PreviewImagesAdapter;
import com.blackgoose.fare_the_well.Adapters.ProgramAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.blackgoose.fare_the_well.Models.ProgramModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserEulogyDetailsActivity extends AppCompatActivity {

    private ImageView mainImageView;
    private ViewPager2 viewPager;
    private RecyclerView programsRv;

    private TextView deceasedFname, deceasedSname, deceasedLname, eulogyId;
    private TextView birthYear, passingYear, burialLocation, eulogyText;
    private TextView authorName, authorPhone, status;
    private ImageButton btnShare;


    private EulogyModel eulogy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_eulogy_detail);

        // Bind UI
        mainImageView = findViewById(R.id.deceased_image);
        viewPager = findViewById(R.id.viewPager);
        programsRv = findViewById(R.id.reviewProgramsRv);
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

        btnShare.setOnClickListener(v -> shareEulogy());


        // Get eulogy from intent
        eulogy = (EulogyModel) getIntent().getSerializableExtra("eulogy");
        if (eulogy == null) {
            Toast.makeText(this, "Eulogy data missing", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        populateData();
    }

    private void shareEulogy() {
        if (eulogy.Eulogyid == null || eulogy.Eulogyid.isEmpty()) {
            Toast.makeText(this, "Unable to share eulogy", Toast.LENGTH_SHORT).show();
            return;
        }

        String shareUrl = "https://farewell.app/eulogy/" + eulogy.Eulogyid;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "In Loving Memory");
        intent.putExtra(Intent.EXTRA_TEXT, shareUrl);

        startActivity(Intent.createChooser(intent, "Share via"));
    }


    private void populateData() {

        // Eulogy ID (Firebase entry key)
        if (eulogy.Eulogyid != null && !eulogy.Eulogyid.isEmpty()) {
            eulogyId.setText(eulogy.Eulogyid);
        } else {
            eulogyId.setText("Eulogy ID: " + eulogy.Eulogyid);
        }

        // Main image
        if (eulogy.mainImageUrl != null && !eulogy.mainImageUrl.isEmpty()) {
            Glide.with(this).load(Uri.parse(eulogy.mainImageUrl)).into(mainImageView);
        }

        // Names
        deceasedFname.setText(eulogy.firstName);
        deceasedSname.setText(eulogy.secondName);
        deceasedLname.setText(eulogy.lastName);

        // Dates
        birthYear.setText(eulogy.birthYear);
        passingYear.setText(eulogy.passingYear);

        // Burial location and eulogy
        burialLocation.setText(eulogy.burialLocation);
        eulogyText.setText(eulogy.eulogyText);

        // Author info
        authorName.setText(eulogy.authorName);
        authorPhone.setText(eulogy.authorPhone);
        status.setText(eulogy.status);

        // Gallery images
        if (eulogy.galleryImages != null && !eulogy.galleryImages.isEmpty()) {
            PreviewImagesAdapter adapter = new PreviewImagesAdapter(this, eulogy.galleryImages);
            viewPager.setAdapter(adapter);
        }

        // Funeral programs
        if (eulogy.funeralPrograms != null && !eulogy.funeralPrograms.isEmpty()) {
            programsRv.setLayoutManager(new LinearLayoutManager(this));
            programsRv.setAdapter(new ProgramAdapter(eulogy.funeralPrograms));
        }
    }
}
