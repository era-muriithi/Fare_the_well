package com.blackgoose.fare_the_well;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashScreenActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        handleDeepLink(getIntent());
    }

    private void handleDeepLink(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {

            // Example: https://farewell.app/eulogy/-Nxyz123
            String eulogyId = data.getLastPathSegment();

            if (eulogyId != null && !eulogyId.isEmpty()) {
                openEulogyFromId(eulogyId);
            }
        }
    }

    private void openEulogyFromId(String eulogyId) {

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("Eulogies")
                .child(eulogyId);

        ref.get().addOnSuccessListener(snapshot -> {
            if (snapshot.exists()) {
                EulogyModel eulogy = snapshot.getValue(EulogyModel.class);
                if (eulogy != null) {
                    eulogy.Eulogyid = snapshot.getKey();

                    Intent intent = new Intent(this, EulogyDetailActivity.class);
                    intent.putExtra("eulogy", eulogy);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(this, "Eulogy not found", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e ->
                Toast.makeText(this, "Failed to load eulogy", Toast.LENGTH_SHORT).show()
        );
    }


}
