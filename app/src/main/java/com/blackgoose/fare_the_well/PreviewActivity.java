package com.blackgoose.fare_the_well;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.Adapters.ProgramAdapter;
import com.blackgoose.fare_the_well.Models.ProgramModel;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreviewActivity extends AppCompatActivity {
    private ArrayList<Uri> imageUris;
    private ArrayList<ProgramModel> programs;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        imageUris = getIntent().getParcelableArrayListExtra("images");
        programs = (ArrayList<ProgramModel>) getIntent().getSerializableExtra("programs");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String firstName = getIntent().getStringExtra("firstName");
        String secondName = getIntent().getStringExtra("secondName");
        String lastName = getIntent().getStringExtra("lastName");
        String dob = getIntent().getStringExtra("dob");
        String passingOn = getIntent().getStringExtra("passingOn");
        String burialLocation = getIntent().getStringExtra("burialLocation");
        String earlyLife = getIntent().getStringExtra("earlyLife");
        String education = getIntent().getStringExtra("education");
        String work = getIntent().getStringExtra("work");
        String family = getIntent().getStringExtra("family");
        String finalMomemts = getIntent().getStringExtra("finalMoments");

        String authorName = getIntent().getStringExtra("authorName");
        String authorContact = getIntent().getStringExtra("authorContact");

        // Initialize progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading, please wait...");
        progressDialog.setCancelable(false);


        // Bind Views
        TextView first_name = findViewById(R.id.deceased_Fname);
        TextView second_name = findViewById(R.id.deceased_Sname);
        TextView last_name = findViewById(R.id.deceased_Lname);
        TextView date_of_birth = findViewById(R.id.decease_death_date);
        TextView passing_date = findViewById(R.id.decease_death_date);
        TextView burial_location = findViewById(R.id.burial_location);
        TextView early_life = findViewById(R.id.earlylife_biography);
        TextView education_bio = findViewById(R.id.education_biography);
        TextView work_bio = findViewById(R.id.work_biography);
        TextView family_bio = findViewById(R.id.family_biography);
        TextView final_moments = findViewById(R.id.final_biography);

        TextView author_name = findViewById(R.id.author_name);
        TextView author_contact = findViewById(R.id.author_phone);

        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        RecyclerView programsRv = findViewById(R.id.reviewProgramsRv);
        Button uploadBtn = findViewById(R.id.uploadBtn);

        // Set text
       first_name.setText(firstName);
        second_name.setText(secondName);
        last_name.setText(lastName);
        date_of_birth.setText(dob);
        passing_date.setText(passingOn);
        burial_location.setText(burialLocation);
        early_life.setText(earlyLife);
        education_bio.setText(education);
        work_bio.setText(work);
        family_bio.setText(family);
        final_moments.setText(finalMomemts);
        author_name.setText(authorName);
        author_contact.setText(authorContact);

        // Setup image slider
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        for (Uri uri : imageUris) {
            slideModels.add(new SlideModel(uri.toString(), ScaleTypes.FIT));
        }
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        // Setup RecyclerView
        programsRv.setLayoutManager(new LinearLayoutManager(this));
        programsRv.setAdapter(new ProgramAdapter(programs));

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                ArrayList<String> imageUrls = new ArrayList<>();
                StorageReference storageRef = FirebaseStorage.getInstance().getReference("eulogyPictures");

                for (int i = 0; i < imageUris.size(); i++) {
                    Uri uri = imageUris.get(i);
                    StorageReference imgRef = storageRef.child(System.currentTimeMillis() + i + ".jpg");
                    imgRef.putFile(uri).continueWithTask(task -> {
                        if (!task.isSuccessful()) throw task.getException();
                        return imgRef.getDownloadUrl();
                    }).addOnSuccessListener(downloadUrl -> {
                        imageUrls.add(downloadUrl.toString());
                        if (imageUrls.size() == imageUris.size()) {
                            saveToDatabase(imageUrls);
                        }
                    }).addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(PreviewActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });


    }
    private void saveToDatabase(List<String> urls) {
        Map<String, Object> data = new HashMap<>();
        data.put("firstName", getIntent().getStringExtra("firstName"));
        data.put("secondName", getIntent().getStringExtra("secondName"));
        data.put("lastName", getIntent().getStringExtra("lastName"));
        data.put("dateOfBirth", getIntent().getStringExtra("dob"));
        data.put("passingOnDate", getIntent().getStringExtra("passingOn"));
        data.put("burialLocation", getIntent().getStringExtra("burialLocation"));
        data.put("early", getIntent().getStringExtra("earlyLife"));
        data.put("education", getIntent().getStringExtra("education"));
        data.put("work", getIntent().getStringExtra("work"));
        data.put("family", getIntent().getStringExtra("family"));
        data.put("finalMoment", getIntent().getStringExtra("finalMoments"));
        data.put("authorName", getIntent().getStringExtra("authorName"));
        data.put("authorPhone", getIntent().getStringExtra("authorContact"));
        data.put("userId", getIntent().getStringExtra("userUid"));
        data.put("imageUrls", urls);
        data.put("funeralPrograms", programs);

        FirebaseDatabase.getInstance().getReference("Eulogies")
                .push().setValue(data)
                .addOnSuccessListener(aVoid -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG).show();

                    returnToMainActivity();
                })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}