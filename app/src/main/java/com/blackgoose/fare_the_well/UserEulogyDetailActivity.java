package com.blackgoose.fare_the_well;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.blackgoose.fare_the_well.Adapters.ImageSliderAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class UserEulogyDetailActivity extends AppCompatActivity {
    ImageView deceased_img;

    Button delete_button;
    TextView deceased_Fname, deceased_Sname, deceased_Lname, deceased_dob, deceased_dod, burial_location, deceased_earlyLife, deceased_education, deceased_work, deceased_family, deceased_finalMoments;
    TextView author_phone, author_name;
    DatabaseReference root;
    String userId;
    private ViewPager viewPager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_eulogy_detail);

            root = FirebaseDatabase.getInstance().getReference();


        viewPager = findViewById(R.id.view_pager_images);

            deceased_img = findViewById(R.id.deceased_image);
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
        userId  = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        String userId1 = getIntent().getStringExtra("user1");
        Toast.makeText(this, ""+userId, Toast.LENGTH_SHORT).show();
        if (userId != null) {
            fetchData(userId1);
        }

//            Bundle bundle = getIntent().getExtras();
//
//            if (bundle != null) {
//                Picasso.get().load(getIntent().getStringExtra("deceasedImage"))
//                        .placeholder(R.drawable.ic_launcher)
//                        .into(deceased_img);
//
//                deceased_Fname.setText(bundle.getString("deceasedFname"));
//                deceased_Sname.setText(bundle.getString("deceasedSname"));
//                deceased_Lname.setText(bundle.getString("deceasedLname"));
//                deceased_dob.setText(bundle.getString("deceaseDob"));
//                deceased_dod.setText(bundle.getString("deceasedDod"));
//                burial_location.setText(bundle.getString("burialLocation"));
//                deceased_earlyLife.setText(bundle.getString("deceasedEarlylife"));
//                deceased_education.setText(bundle.getString("deceasedEducation"));
//                deceased_work.setText(bundle.getString("deceasedWork"));
//                deceased_family.setText(bundle.getString("deceasedFamily"));
//                deceased_finalMoments.setText(bundle.getString("deceaseFinalMoments"));
//
//            }

    }

    private void fetchData(String user1) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference()
                .child("Eulogies").child(userId)
                .child(user1); // Use user1 as the key to get the specific eulogy data

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
                            Log.d("url", imageUrls.toString());
                            ImageSliderAdapter imageSliderAdapter = new ImageSliderAdapter(UserEulogyDetailActivity.this, imageUrls);
                            viewPager.setAdapter(imageSliderAdapter);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserEulogyDetailActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}



