package com.blackgoose.fare_the_well;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 1001;

    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth firebaseAuth;

    private CheckBox termsCheckBox;
    private SignInButton signInButton; // FIX 1: Declare as field so we can use it in all methods

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        // UI
        signInButton = findViewById(R.id.sign_in_button);
        termsCheckBox = findViewById(R.id.checkbox_privacy);

        // Disable button until terms are accepted
        signInButton.setEnabled(false);

        termsCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            signInButton.setEnabled(isChecked);
        });

        // Handle sign-in click
        signInButton.setOnClickListener(v -> {
            if (!termsCheckBox.isChecked()) {
                Toast.makeText(this, "Please accept the terms to continue", Toast.LENGTH_SHORT).show();
                return;
            }
            startGoogleSignIn();
        });

        // Initialize Google Client + FirebaseAuth
        configureGoogleClient();
        firebaseAuth = FirebaseAuth.getInstance(); // FIX 2: Ensure firebaseAuth initialized in onCreate
    }

    @Override
    protected void onStart() {
        super.onStart();

        // FIX 3: firebaseAuth is now guaranteed to be initialized
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            // Redirect signed user
            openUserPageActivity();
        }
    }

    private void configureGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // UI size
        signInButton.setSize(SignInButton.SIZE_WIDE);
    }

    private void startGoogleSignIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                showToast("Google Sign-In Successful");
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                showToast("Google Sign-In Failed");
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        Log.d(TAG, "Firebase Auth success: " + user.getEmail());
                        showToast("Authentication Successful");
                        openUserPageActivity();
                    } else {
                        Log.w(TAG, "Firebase Authentication failed", task.getException());
                        showToast("Authentication Failed");
                    }
                });
    }

    private void openUserPageActivity() {
        startActivity(new Intent(this, UserpageActivity.class));
        finish();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
