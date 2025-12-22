package com.blackgoose.fare_the_well.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.blackgoose.fare_the_well.R;

import java.util.List;

public class EulogyListAdapter extends RecyclerView.Adapter<EulogyListAdapter.VH> {

    public interface OnItemClickListener {
        void onItemClick(EulogyModel model);
    }

    private List<EulogyModel> items;
    private OnItemClickListener listener;

    public EulogyListAdapter(List<EulogyModel> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eulogy_custom_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        EulogyModel e = items.get(position);
        // Display full name: first + second (if exists) + last
        String fullName = (e.firstName != null ? e.firstName : "") +
                (e.secondName != null && !e.secondName.isEmpty() ? " " + e.secondName : "") +
                (e.lastName != null && !e.lastName.isEmpty() ? " " + e.lastName : "");

        // Set TextViews

        holder.first_name.setText(e.firstName != null ? e.firstName : "");
        holder.second_name.setText(e.secondName != null ? e.secondName : "");
        holder.last_name.setText(e.lastName != null ? e.lastName : "");
        holder.birth.setText(e.birthYear != null ? e.birthYear : "-");
        holder.passing.setText(e.passingYear != null ? e.passingYear : "-");

        if (e.mainImageUrl != null && !e.mainImageUrl.isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(e.mainImageUrl)
                    .placeholder(R.drawable.gallery)
                    .error(R.drawable.gallery)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.gallery);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(e);
        });
    }

    @Override
    public int getItemCount() { return items == null ? 0 : items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        ImageView image;
        TextView first_name, second_name, last_name, birth, passing;
        VH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img);
            first_name = itemView.findViewById(R.id.Fname);
            second_name = itemView.findViewById(R.id.Sname);
            last_name = itemView.findViewById(R.id.Lname);
            birth = itemView.findViewById(R.id.born_dates);
            passing = itemView.findViewById(R.id.death_date);
        }
    }
}

