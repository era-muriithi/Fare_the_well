package com.blackgoose.fare_the_well.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.blackgoose.fare_the_well.Models.ProgramModel;
import com.blackgoose.fare_the_well.R;

import java.util.List;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.VH> {

    private List<ProgramModel> list;

    public ProgramAdapter(List<ProgramModel> list) { this.list = list; }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_fragment_lit_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ProgramModel m = list.get(position);
        holder.time.setText(m.getStartTime());
        holder.action.setText(m.getAction());
    }

    @Override
    public int getItemCount() { return list == null ? 0 : list.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView time, action;
        VH(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.startTime);
            action = itemView.findViewById(R.id.action);
        }
    }
}
