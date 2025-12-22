package com.blackgoose.fare_the_well;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class EulogyDetailActivity extends AppCompatActivity {

    public static final String EXTRA_EULOGY = "eulogy";

    private EulogyModel eulogy;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private String[] tabTitles = {"Eulogy", "Funeral Program"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eulogy_details_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        eulogy = (EulogyModel) getIntent().getSerializableExtra(EXTRA_EULOGY);
        if (eulogy == null) finish();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.detailViewPager);

        viewPager.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                if (position == 0) {
                    return EulogyTabFragment.newInstance(eulogy);
                } else {
                    return ProgramFragment.newInstance(eulogy);
                }
            }

            @Override
            public int getItemCount() { return 2; }
        });

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();
    }
}

