package com.blackgoose.fare_the_well;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.blackgoose.fare_the_well.Adapters.EulogyAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    EulogyAdapter eulogyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Getting reference of recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.eulogylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FirebaseRecyclerOptions<EulogyModel> options = new FirebaseRecyclerOptions.Builder<EulogyModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Eulogies"), EulogyModel.class)
                .build();

        eulogyAdapter = new EulogyAdapter(options);
        recyclerView.setAdapter(eulogyAdapter);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String ignore = query;
                query.equalsIgnoreCase(ignore);
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });
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

    private void txtSearch(String str) {
        FirebaseRecyclerOptions<EulogyModel> options = new FirebaseRecyclerOptions.Builder<EulogyModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Eulogies").orderByChild("deceasedFname").startAt(str).endAt(str + "\uf8ff"), EulogyModel.class)
                .build();

        eulogyAdapter = new EulogyAdapter(options);
        eulogyAdapter.startListening();
        recyclerView.setAdapter(eulogyAdapter);

    }
}