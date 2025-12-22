package com.blackgoose.fare_the_well;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.blackgoose.fare_the_well.Adapters.FullScreenAdapter;

import java.util.ArrayList;

public class FullscreenActivity extends AppCompatActivity {

    private static final String EXTRA_IMAGES = "extra_images";
    private static final String EXTRA_POSITION = "extra_position";

    private ViewPager2 viewPager;
    private ArrayList<String> images;
    private int startPosition;

    public static void open(Context context, ArrayList<String> images, int position) {
        Intent intent = new Intent(context, FullscreenActivity.class);
        intent.putStringArrayListExtra(EXTRA_IMAGES, images);
        intent.putExtra(EXTRA_POSITION, position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        viewPager = findViewById(R.id.fullscreenViewPager);

        images = getIntent().getStringArrayListExtra(EXTRA_IMAGES);
        startPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);

        if (images != null && !images.isEmpty()) {
            FullScreenAdapter adapter = new FullScreenAdapter(this, images);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(startPosition, false);
        }

    }
}
