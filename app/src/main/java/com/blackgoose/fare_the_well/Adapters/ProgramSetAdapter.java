package com.blackgoose.fare_the_well.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.Models.ProgramModel;
import com.blackgoose.fare_the_well.R;

import java.util.ArrayList;

public class ProgramSetAdapter extends RecyclerView.Adapter<ProgramSetAdapter.ProgramViewHolder> {

    private final ArrayList<ProgramModel> programList;

    public ProgramSetAdapter(ArrayList<ProgramModel> programList) {
        this.programList = programList;
    }

    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.program_item, parent, false);
        return new ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder holder, int position) {
        ProgramModel program = programList.get(position);

        holder.txtTime.setText(program.getStartTime());
        holder.txtAction.setText(program.getAction());

        holder.btnRemove.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                programList.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, programList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }

    static class ProgramViewHolder extends RecyclerView.ViewHolder {

        TextView txtTime, txtAction;
        ImageView btnRemove;

        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.StarttextTime);
            txtAction = itemView.findViewById(R.id.textActivity);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }
}

