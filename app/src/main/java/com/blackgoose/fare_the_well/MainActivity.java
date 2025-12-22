package com.blackgoose.fare_the_well;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.Adapters.EulogyListAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements EulogyListAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView emptyText;
    private EditText etSearch;

    private EulogyListAdapter adapter;

    // 🔹 Full dataset
    private final ArrayList<EulogyModel> allEulogies = new ArrayList<>();

    // 🔹 Filtered dataset
    private final ArrayList<EulogyModel> filteredEulogies = new ArrayList<>();

    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        recyclerView = findViewById(R.id.eulogylist);
        progressBar = findViewById(R.id.progressBar);
        emptyText = findViewById(R.id.eulogyListEmpty);
        etSearch = findViewById(R.id.etSearch);

        adapter = new EulogyListAdapter(filteredEulogies, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ref = FirebaseDatabase.getInstance().getReference("Eulogies");

        loadConfirmedEulogies();
        setupSearch();
    }

    private void loadConfirmedEulogies() {
        progressBar.setVisibility(View.VISIBLE);

        Query confirmedQuery = ref
                .orderByChild("status")
                .equalTo("confirmed");

        confirmedQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allEulogies.clear();
                filteredEulogies.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    EulogyModel e = ds.getValue(EulogyModel.class);
                    if (e != null) {
                        e.Eulogyid = ds.getKey();
                        allEulogies.add(e);
                    }
                }

                // Initially show all confirmed
                filteredEulogies.addAll(allEulogies);

                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                emptyText.setVisibility(filteredEulogies.isEmpty() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                emptyText.setText("Failed to load eulogies.");
                emptyText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setupSearch() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterByFirstName(s.toString());
            }

            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void filterByFirstName(String query) {
        filteredEulogies.clear();

        if (query.isEmpty()) {
            filteredEulogies.addAll(allEulogies);
        } else {
            String searchText = query.toLowerCase(Locale.getDefault());

            for (EulogyModel e : allEulogies) {
                if (e.firstName != null &&
                        e.firstName.toLowerCase(Locale.getDefault())
                                .contains(searchText)) {
                    filteredEulogies.add(e);
                }
            }
        }

        adapter.notifyDataSetChanged();
        emptyText.setVisibility(filteredEulogies.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onItemClick(EulogyModel model) {
        Intent i = new Intent(this, EulogyDetailActivity.class);
        i.putExtra("eulogy", model);
        startActivity(i);
    }
}
