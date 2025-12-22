package com.blackgoose.fare_the_well;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.Adapters.ProgramAdapter;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.blackgoose.fare_the_well.Models.ProgramModel;

import java.util.ArrayList;

public class ProgramFragment extends Fragment {

    private static final String ARG_EULOGY = "arg_eulogy";
    private EulogyModel eulogy;

    public static ProgramFragment newInstance(EulogyModel model) {
        ProgramFragment f = new ProgramFragment();
        Bundle b = new Bundle();
        b.putSerializable(ARG_EULOGY, model);
        f.setArguments(b);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        return inflater.inflate(R.layout.program_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, android.os.Bundle savedInstanceState) {
        if (getArguments() != null) {
            eulogy = (EulogyModel) getArguments().getSerializable(ARG_EULOGY);
        }

        TextView fullName = view.findViewById(R.id.fullName);

        String first = eulogy.firstName != null ? eulogy.firstName : "";
        String second = eulogy.secondName != null ? eulogy.secondName : "";

        fullName.setText("In Loving Memory of " + first + "\n" + second);


        RecyclerView rv = view.findViewById(R.id.recyclerPrograms);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

        ArrayList<ProgramModel> programs = eulogy == null || eulogy.funeralPrograms == null
                ? new ArrayList<>() : eulogy.funeralPrograms;

        ProgramAdapter adapter = new ProgramAdapter(programs);
        rv.setAdapter(adapter);
    }
}
