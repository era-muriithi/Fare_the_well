package com.blackgoose.fare_the_well;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blackgoose.fare_the_well.Adapters.EulogyAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EulogyTabFragment extends Fragment {

    private static final String ARG_EULOGY = "arg_eulogy";
    private EulogyModel eulogy;

    public static EulogyTabFragment newInstance(EulogyModel model) {
        EulogyTabFragment f = new EulogyTabFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARG_EULOGY, model);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.biography_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            eulogy = (EulogyModel) getArguments().getSerializable(ARG_EULOGY);
        }
        if (eulogy == null) return;

        ImageView mainImage = view.findViewById(R.id.detailMainImage);
        TextView fname = view.findViewById(R.id.deceased_Fname);
        TextView sname = view.findViewById(R.id.deceased_Sname);
        TextView lname = view.findViewById(R.id.deceased_Lname);
        TextView dobDates = view.findViewById(R.id.deceased_born_dates);
        TextView dodDates = view.findViewById(R.id.decease_death_date);
        TextView tvEulogy = view.findViewById(R.id.life_biography);
        TextView tvLocation = view.findViewById(R.id.burial_location);

        // Set main image
        if (eulogy.mainImageUrl != null && !eulogy.mainImageUrl.isEmpty()) {
            Glide.with(requireContext())
                    .load(eulogy.mainImageUrl)
                    .placeholder(R.drawable.gallery)
                    .into(mainImage);
        }

        // Set text fields
        fname.setText(eulogy.firstName != null ? eulogy.firstName : "");
        sname.setText(eulogy.secondName != null ? eulogy.secondName : "");
        lname.setText(eulogy.lastName != null ? eulogy.lastName : "");

        dobDates.setText((eulogy.birthYear != null ? eulogy.birthYear : "-"));
        dodDates.setText((eulogy.passingYear != null ? eulogy.passingYear : "-"));
        tvEulogy.setText(eulogy.eulogyText != null ? eulogy.eulogyText : "");
        tvLocation.setText(eulogy.burialLocation != null ? eulogy.burialLocation : "");


        GridView gridView = view.findViewById(R.id.galleryGridView);

        ArrayList<String> galleryImages =
                eulogy.galleryImages != null ? eulogy.galleryImages : new ArrayList<>();

        EulogyAdapter adapter = new EulogyAdapter(
                requireContext(),
                galleryImages,
                position -> FullscreenActivity.open(requireContext(), galleryImages, position)
        );

        gridView.setAdapter(adapter);

    }
}
