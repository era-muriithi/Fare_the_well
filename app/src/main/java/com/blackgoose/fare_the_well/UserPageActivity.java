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
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage_activity);
        logoutBtn = findViewById(R.id.logout_button);
        userEmail = findViewById(R.id.userEmail);
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        textView = findViewById(R.id.termsPrivacy);
        textView.setMovementMethod(LinkMovementMethod.getInstance());


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userEmail.setText(currentUser.getEmail());
        }

        recyclerView = (RecyclerView) findViewById(R.id.Usereulogylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseRecyclerOptions<EulogyModel> options = new FirebaseRecyclerOptions.Builder<EulogyModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Eulogies").orderByChild("userUid").equalTo(userId), EulogyModel.class)
                .build();

        eulogyAdapter = new UserEulogyAdapter(options);
        recyclerView.setAdapter(eulogyAdapter);


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


    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.getRecycledViewPool().clear();
        eulogyAdapter.notifyDataSetChanged();
        eulogyAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        eulogyAdapter.stopListening();
            }
}
