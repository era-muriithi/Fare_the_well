package com.blackgoose.fare_the_well;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_home);

        Button btnOpenNext = findViewById(R.id.browse_button);
        Button btnOpenEulogyInput = findViewById(R.id.newEulogy_button);
        Button btnAccount = findViewById(R.id.account_button);

        btnOpenNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnOpenEulogyInput.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser != null) {

                startActivity(new Intent(this, EulogyInputActivity.class));
            } else {
                // User not signed in → go to SigninActivity
                startActivity(new Intent(this, SignInActivity.class));
            }
        });

        btnAccount.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser != null) {
                // User is signed in → go to UserpageActivity
                startActivity(new Intent(this, UserpageActivity.class));
            } else {
                // User not signed in → go to SigninActivity
                startActivity(new Intent(this, SignInActivity.class));
            }
        });



    }
}