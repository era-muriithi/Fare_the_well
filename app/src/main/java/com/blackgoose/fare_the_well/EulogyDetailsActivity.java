package com.blackgoose.fare_the_well;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.blackgoose.fare_the_well.Adapters.EulogyPagerAdapter;
import com.blackgoose.fare_the_well.Adapters.ViewPagerAdapterEulogy;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.denzcoskun.imageslider.adapters.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

public class EulogyDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eulogy_details_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        // Retrieve the Eulogy object passed via the Intent
        EulogyModel eulogy = (EulogyModel) getIntent().getSerializableExtra("eulogy");

        if (eulogy != null) {
            ViewPager2 viewPager = findViewById(R.id.viewPager);
            TabLayout tabLayout = findViewById(R.id.tabLayout);

            ViewPagerAdapterEulogy adapter = new ViewPagerAdapterEulogy(this, eulogy);
            viewPager.setAdapter(adapter);

            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                if (position == 0) tab.setText("Biography");
                else tab.setText("Program");
            }).attach();
        }else {
            Toast.makeText(this, "Eulogy data is missing", Toast.LENGTH_SHORT).show();
        }

    }
}
