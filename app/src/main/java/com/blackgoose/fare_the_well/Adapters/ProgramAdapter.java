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

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.TaskViewHolder> {

    private List<ProgramModel> programList;

    public ProgramAdapter(List<ProgramModel> programList) {
        this.programList = programList;
    }
    @NonNull
    @Override
    public ProgramAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.program_list_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        ProgramModel programModel = programList.get(position);
        holder.start_time.setText(programModel.getStarttime());
        holder.completion_time.setText(programModel.getCompletiontime());
        holder.action.setText(programModel.getAction());
    }

    @Override
    public int getItemCount() {
        return programList.size();
    }
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView start_time,completion_time, action;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            start_time = itemView.findViewById(R.id.startTime);
            completion_time = itemView.findViewById(R.id.completionTime);
            action = itemView.findViewById(R.id.action);
        }
    }
}

