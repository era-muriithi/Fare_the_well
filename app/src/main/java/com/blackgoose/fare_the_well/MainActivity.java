package com.blackgoose.fare_the_well;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.blackgoose.fare_the_well.Adapters.EulogyAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EulogyAdapter eulogyAdapter;
    private ArrayList<EulogyModel> eulogyList;
    private ProgressBar progressBar;
    List<EulogyModel> filteredEulogyList;
    SearchView searchView;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting reference of recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.eulogylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eulogyList = new ArrayList<>();
        filteredEulogyList = new ArrayList<>();
        eulogyAdapter = new EulogyAdapter(this, filteredEulogyList); // use filtered list
        recyclerView.setAdapter(eulogyAdapter);
        database = FirebaseDatabase.getInstance();

        searchView = findViewById(R.id.searchView);
        progressBar = findViewById(R.id.progressBar);

        fetchEulogies();

        // Set up the search listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterEulogies(newText);
                return true;
            }
        });
    }


    private void fetchEulogies() {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        database.getReference("Eulogies")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        eulogyList.clear();  // Clear existing list
                        for (DataSnapshot data : snapshot.getChildren()) {
                            EulogyModel eulogy = data.getValue(EulogyModel.class);
                            if (eulogy != null) {
                                eulogy.setKey(data.getKey());  // Save the key
                                eulogyList.add(eulogy);
                            }
                        }
                        // After fetching data, update the filtered list and notify the adapter
                        filteredEulogyList.clear();
                        filteredEulogyList.addAll(eulogyList);  // Add all eulogies initially
                        eulogyAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void filterEulogies(String query) {
        filteredEulogyList.clear();  // Clear the filtered list
        if (query.isEmpty()) {
            filteredEulogyList.addAll(eulogyList);  // If query is empty, show all eulogies
        } else {
            for (EulogyModel eulogy : eulogyList) {
                if (eulogy.getFirstName().toLowerCase().contains(query.toLowerCase())) {
                    filteredEulogyList.add(eulogy);  // If first name matches, add to filtered list
                }
            }
        }
        eulogyAdapter.notifyDataSetChanged();  // Notify the adapter to update the RecyclerView
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Switching on the item id of the menu item
        if (item.getItemId() == R.id.addEulogy) {
            openEulogyInputActivity();
            return true;
        } else if (item.getItemId() == R.id.userProfile) {
            openSigninActivity();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void openSigninActivity() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            Intent intent = new Intent(this, UserPageActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }
    }

    private void openEulogyInputActivity() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(this, EulogyInputActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }
    }
}

