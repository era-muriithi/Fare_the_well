package com.blackgoose.fare_the_well;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.blackgoose.fare_the_well.Adapters.ImageSliderAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class BiographyFragment extends Fragment {
    private EulogyModel eulogy;
    TextView deceased_Fname, deceased_Sname, deceased_Lname, deceased_dob, deceased_dod, burial_location, deceased_earlyLife,
            deceased_education, deceased_work, deceased_family, deceased_finalMoments;

    public static BiographyFragment newInstance(EulogyModel eulogy) {
        BiographyFragment fragment = new BiographyFragment();
        Bundle args = new Bundle();
        args.putSerializable("eulogy", eulogy);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.biography_fragment, container, false);
        // Retrieving the Eulogy object using the same key "eulogy"
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("eulogy")) {
            eulogy = (EulogyModel) bundle.getSerializable("eulogy");
            if (eulogy != null) {
                // Initialize the SliderView
                SliderView sliderView = view.findViewById(R.id.imageSlider);

                // Check if there are any images in the eulogy
                if (eulogy.getImageUrls() != null && !eulogy.getImageUrls().isEmpty()) {
                    List<String> imageUrls = eulogy.getImageUrls();

                    // Create the adapter and set it to the SliderView
                    ImageSliderAdapter adapter = new ImageSliderAdapter(imageUrls);
                    sliderView.setSliderAdapter(adapter);
                    sliderView.setAutoCycle(true);  // Enable automatic scrolling
                    sliderView.startAutoCycle();
                }

                TextView deceased_Fname = view.findViewById(R.id.deceased_Fname);
                TextView deceased_Sname = view.findViewById(R.id.deceased_Sname);
                TextView deceased_Lname = view.findViewById(R.id.deceased_Lname);
                TextView deceased_dob = view.findViewById(R.id.deceased_born_dates);
                TextView deceased_dod = view.findViewById(R.id.decease_death_date);
                TextView burial_location = view.findViewById(R.id.burial_location);
                TextView deceased_earlyLife = view.findViewById(R.id.earlylife_biography);
                TextView deceased_education = view.findViewById(R.id.education_biography);
                TextView deceased_work = view.findViewById(R.id.work_biography);
                TextView deceased_family = view.findViewById(R.id.family_biography);
                TextView deceased_finalMoments = view.findViewById(R.id.final_biography);

                deceased_Fname.setText(eulogy.getFirstName());
                deceased_Sname.setText(eulogy.getSecondName());
                deceased_Lname.setText(eulogy.getLastName());
                deceased_dob.setText(eulogy.getDateOfBirth());
                deceased_dod.setText(eulogy.getPassingOnDate());
                burial_location.setText(eulogy.getBurialLocation());
                deceased_earlyLife.setText(eulogy.getEarly());
                deceased_education.setText(eulogy.getEducation());
                deceased_work.setText(eulogy.getWork());
                deceased_family.setText(eulogy.getFamily());
                deceased_finalMoments.setText(eulogy.getFinalMoment());
            } else {
                Toast.makeText(getContext(), "Missing biography data", Toast.LENGTH_SHORT).show();

                return view;
            }
        }
        return view;
    }
}


