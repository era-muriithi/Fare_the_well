package com.blackgoose.fare_the_well;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.Adapters.UserEulogyAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserpageActivity extends AppCompatActivity {

    private RecyclerView eulogyRecyclerView;
    private UserEulogyAdapter adapter;
    private ArrayList<EulogyModel> userEulogies;

    private FirebaseUser currentUser;
    private DatabaseReference eulogyRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage_activity);

        eulogyRecyclerView = findViewById(R.id.Usereulogylist);
        eulogyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        userEulogies = new ArrayList<>();
        adapter = new UserEulogyAdapter(this, userEulogies);
        eulogyRecyclerView.setAdapter(adapter);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not signed in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        eulogyRef = FirebaseDatabase.getInstance().getReference("Eulogies");
        fetchUserEulogies();
    }

    private void fetchUserEulogies() {
        eulogyRef.orderByChild("userId").equalTo(currentUser.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        userEulogies.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            EulogyModel model = ds.getValue(EulogyModel.class);
                            if (model != null) {
                                model.Eulogyid = ds.getKey(); // store firebase key
                                // Convert galleryImages and funeralPrograms to ArrayList
                                if (model.galleryImages == null) model.galleryImages = new ArrayList<>();
                                if (model.funeralPrograms == null) model.funeralPrograms = new ArrayList<>();
                                userEulogies.add(model);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UserpageActivity.this, "Failed to load eulogies: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
