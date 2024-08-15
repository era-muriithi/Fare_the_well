//package com.blackgoose.fare_the_well;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//import com.squareup.picasso.Picasso;
//
//public class EulogyDetailsActivity extends AppCompatActivity {
//    ImageView deceased_image;
//    TextView shareLink, deceased_Fname, deceased_Sname, deceased_Lname, deceased_dob, deceased_dod, burial_location, deceased_earlyLife, deceased_education, deceased_work, deceased_family, deceased_finalMoments;
//    TextView author_name, author_phone;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.eulogy_details_activity);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().hide();
//        }
//
//        deceased_image = findViewById(R.id.deceased_image);
//        deceased_Fname = findViewById(R.id.deceased_Fname);
//        deceased_Sname = findViewById(R.id.deceased_Sname);
//        deceased_Lname = findViewById(R.id.deceased_Lname);
//        deceased_dob = findViewById(R.id.deceased_born_dates);
//        deceased_dod = findViewById(R.id.decease_death_date);
//        burial_location = findViewById(R.id.burial_location);
//        deceased_earlyLife = findViewById(R.id.earlylife_biography);
//        deceased_education = findViewById(R.id.education_biography);
//        deceased_work = findViewById(R.id.work_biography);
//        deceased_family = findViewById(R.id.family_biography);
//        deceased_finalMoments = findViewById(R.id.final_biography);
//        shareLink = findViewById(R.id.share_link);
//
//
//        Bundle bundle = getIntent().getExtras();
//
//        if (bundle != null) {
//            Picasso.get().load(getIntent().getStringExtra("deceasedImage"))
//                    .placeholder(R.drawable.person_24)
//                    .into(deceased_image);
//
//            deceased_Fname.setText(bundle.getString("deceasedFname"));
//            deceased_Sname.setText(bundle.getString("deceasedSname"));
//            deceased_Lname.setText(bundle.getString("deceasedLname"));
//            deceased_dob.setText(bundle.getString("deceaseDob"));
//            deceased_dod.setText(bundle.getString("deceasedDod"));
//            burial_location.setText(bundle.getString("burialLocation"));
//            deceased_earlyLife.setText(bundle.getString("deceasedEarlylife"));
//            deceased_education.setText(bundle.getString("deceasedEducation"));
//            deceased_work.setText(bundle.getString("deceasedWork"));
//            deceased_family.setText(bundle.getString("deceasedFamily"));
//            deceased_finalMoments.setText(bundle.getString("deceaseFinalMoments"));
//
//        }
//
//        shareLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "Install farewell app");
//                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.blackgoose.fare_the_well");
//                startActivity(Intent.createChooser(intent, "Share via"));
//            }
//        });
//    }
//}
package com.blackgoose.fare_the_well;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.blackgoose.fare_the_well.Adapters.ImageSliderAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EulogyDetailsActivity extends AppCompatActivity {
    ImageView deceased_image;
    TextView shareLink, deceased_Fname, deceased_Sname, deceased_Lname, deceased_dob, deceased_dod, burial_location, deceased_earlyLife, deceased_education, deceased_work, deceased_family, deceased_finalMoments;
    TextView author_name, author_phone;

    private ViewPager viewPager;
    private List<String> imageUrls = new ArrayList<>();
    private ImageSliderAdapter imageSliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eulogy_details_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        viewPager = findViewById(R.id.view_pager_images);
        deceased_Fname = findViewById(R.id.deceased_Fname);
        deceased_Sname = findViewById(R.id.deceased_Sname);
        deceased_Lname = findViewById(R.id.deceased_Lname);
        deceased_dob = findViewById(R.id.deceased_born_dates);
        deceased_dod = findViewById(R.id.decease_death_date);
        burial_location = findViewById(R.id.burial_location);
        deceased_earlyLife = findViewById(R.id.earlylife_biography);
        deceased_education = findViewById(R.id.education_biography);
        deceased_work = findViewById(R.id.work_biography);
        deceased_family = findViewById(R.id.family_biography);
        deceased_finalMoments = findViewById(R.id.final_biography);
        shareLink = findViewById(R.id.share_link);


        String userId = getIntent().getStringExtra("user1");
        Toast.makeText(this, ""+userId, Toast.LENGTH_SHORT).show();
        if (userId != null) {
            fetchData(userId);
        }


        shareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Install farewell app");
                intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.blackgoose.fare_the_well");
                startActivity(Intent.createChooser(intent, "Share via"));
            }
        });
    }

    private void fetchData(String userId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Eulogies").child(userId);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    EulogyModel eulogy = dataSnapshot.getValue(EulogyModel.class);

                    if (eulogy != null) {
                        deceased_Fname.setText(eulogy.getDeceasedFname());
                        deceased_Sname.setText(eulogy.getDeceasedSname());
                        deceased_Lname.setText(eulogy.getDeceasedLname());
                        deceased_dob.setText(eulogy.getDateOfBirth());
                        deceased_dod.setText(eulogy.getDateOfDeath());
                        burial_location.setText(eulogy.getBurialLocation());
                        deceased_earlyLife.setText(eulogy.getEarlylifeBiography());
                        deceased_education.setText(eulogy.getEducationBiography());
                        deceased_family.setText(eulogy.getFamilyBiography());
                        deceased_work.setText(eulogy.getWorkBiography());
                        deceased_finalMoments.setText(eulogy.getFinalMoments());

                        List<String> imageUrls = eulogy.getDeceasedPictures();
                        if (imageUrls != null && !imageUrls.isEmpty()) {

                            Log.d("url" ,imageUrls.toString());
                            ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(EulogyDetailsActivity.this, imageUrls);
                            viewPager.setAdapter(imageSliderAdapter);
                        }
//                        imageUrls.clear();
//                        if (eulogy.getDeceasedPictures() != null) {
//                            imageUrls.addAll(eulogy.getDeceasedPictures());
//                        }
//
//                        // Set up the image slider
//                        imageSliderAdapter = new ImageSliderAdapter(EulogyDetailsActivity.this, imageUrls);
//                        viewPager.setAdapter(imageSliderAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}