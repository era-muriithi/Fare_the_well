package com.blackgoose.fare_the_well;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.Adapters.ProgramAdapter;
import com.blackgoose.fare_the_well.Models.ProgramModel;

import java.util.ArrayList;
import java.util.List;

public class ProgramFragment extends Fragment {
    private List<ProgramModel> programList;

    public static ProgramFragment newInstance(List<ProgramModel> programs) {
        ProgramFragment fragment = new ProgramFragment();
        Bundle args = new Bundle();
        args.putSerializable("programs", new ArrayList<>(programs));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.program_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerPrograms);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        programList = (List<ProgramModel>) getArguments().getSerializable("programs");
        recyclerView.setAdapter(new ProgramAdapter(programList));

        return view;
    }
}
