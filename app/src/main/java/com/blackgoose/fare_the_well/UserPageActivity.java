package com.blackgoose.fare_the_well;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.Adapters.UserEulogyAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserPageActivity extends AppCompatActivity {
    GoogleSignInClient googleSignInClient;

    GoogleSignInOptions gso;
    private FirebaseAuth firebaseAuth;
    Button logoutBtn;
    TextView userEmail;
    RecyclerView recyclerView;
    UserEulogyAdapter eulogyAdapter;

    FirebaseDatabase database;
    FirebaseStorage firebaseStorage;
    Context context;
    TextView textView;
    List<EulogyModel> eulogyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage_activity);
        logoutBtn = findViewById(R.id.logout_button);
        userEmail = findViewById(R.id.userEmail);
        recyclerView = findViewById(R.id.Usereulogylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        textView = findViewById(R.id.termsPrivacy);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        eulogyList = new ArrayList<>();
        eulogyAdapter = new UserEulogyAdapter(this, eulogyList);
        recyclerView.setAdapter(eulogyAdapter);
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userEmail.setText(currentUser.getEmail());
        }
        fetchUserEulogies(userId);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signoutfarewell();
            }

        });

    }


    private void fetchUserEulogies(String userId) {
        database.getReference("Eulogies")
                .orderByChild("userId")
                .equalTo(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        eulogyList.clear();
                        for (DataSnapshot data : snapshot.getChildren()) {
                            EulogyModel eulogy = data.getValue(EulogyModel.class);
                            if (eulogy != null) {
                                eulogy.setKey(data.getKey()); // Save the key

                                // Check if imageUrls is stored incorrectly as a single String
                                Object imageUrlsData = data.child("imageUrls").getValue();
                                if (imageUrlsData instanceof String) {
                                    // Convert single string URL to a list
                                    List<String> correctedList = new ArrayList<>();
                                    correctedList.add((String) imageUrlsData);
                                    eulogy.setImageUrls(correctedList);
                                } else if (imageUrlsData instanceof List) {
                                    // Correct format, cast safely
                                    eulogy.setImageUrls((List<String>) imageUrlsData);
                                } else {
                                    // If null or another type, set an empty list
                                    eulogy.setImageUrls(new ArrayList<>());
                                }

                                eulogyList.add(eulogy);
                            }
                        }
                        eulogyAdapter.notifyDataSetChanged();
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(UserPageActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void signoutfarewell() {

        //logout completely from google sign-in client

        firebaseAuth.signOut();

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(UserPageActivity.this, "Logged out", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
