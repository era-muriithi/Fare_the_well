package com.blackgoose.fare_the_well.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.blackgoose.fare_the_well.BiographyFragment;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.blackgoose.fare_the_well.ProgramFragment;

public class ViewPagerAdapterEulogy extends FragmentStateAdapter {
    private final EulogyModel eulogy;

    public ViewPagerAdapterEulogy(FragmentActivity activity, EulogyModel eulogy) {
        super(activity);
        this.eulogy = eulogy;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return BiographyFragment.newInstance(eulogy);
        } else {
            return ProgramFragment.newInstance(eulogy.getFuneralPrograms());
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
