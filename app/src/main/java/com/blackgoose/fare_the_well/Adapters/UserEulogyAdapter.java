package com.blackgoose.fare_the_well.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.blackgoose.fare_the_well.R;
import com.blackgoose.fare_the_well.UserEulogyDetailsActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import com.google.firebase.database.FirebaseDatabase;

public class UserEulogyAdapter extends RecyclerView.Adapter<UserEulogyAdapter.ViewHolder> {

    private Context context;
    private ArrayList<EulogyModel> eulogies;

    public UserEulogyAdapter(Context context, ArrayList<EulogyModel> eulogies) {
        this.context = context;
        this.eulogies = eulogies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_eulogy_custom_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EulogyModel model = eulogies.get(position);

        // Load main image
        if (model.mainImageUrl != null && !model.mainImageUrl.isEmpty()) {
            Glide.with(context)
                    .load(model.mainImageUrl)
                    .placeholder(R.drawable.gallery)
                    .into(holder.img);
        }

        // Names
        holder.Fname.setText(model.firstName != null ? model.firstName : "");
        holder.Sname.setText(model.secondName != null ? model.secondName : "");
        holder.Lname.setText(model.lastName != null ? model.lastName : "");

        // Dates
        holder.born_dates.setText(model.birthYear != null ? model.birthYear : "-");
        holder.death_date.setText(model.passingYear != null ? model.passingYear : "-");

        // Status
        holder.status.setText(model.status);

        // Delete button
        holder.deleteBtn.setOnClickListener(v -> {

            if (model.Eulogyid == null) {
                Toast.makeText(context, "Invalid eulogy ID", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(context)
                    .setTitle("Delete Eulogy")
                    .setMessage("Are you sure you want to permanently delete this eulogy? This action cannot be undone.")
                    .setCancelable(true)
                    .setPositiveButton("Delete", (dialog, which) -> {

                        FirebaseDatabase.getInstance()
                                .getReference("Eulogies")
                                .child(model.Eulogyid)
                                .removeValue()
                                .addOnSuccessListener(aVoid ->
                                        Toast.makeText(context, "Eulogy deleted", Toast.LENGTH_SHORT).show()
                                )
                                .addOnFailureListener(e ->
                                        Toast.makeText(context, "Failed to delete: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                );

                    })
                    .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                    .show();
        });


        // Click item to view details
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserEulogyDetailsActivity.class);
            intent.putExtra("eulogy", model); // EulogyModel implements Serializable
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return eulogies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView Fname, Sname, Lname, born_dates, death_date, status;
        AppCompatButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            Fname = itemView.findViewById(R.id.Fname);
            Sname = itemView.findViewById(R.id.Sname);
            Lname = itemView.findViewById(R.id.Lname);
            born_dates = itemView.findViewById(R.id.born_dates);
            death_date = itemView.findViewById(R.id.death_date);
            status = itemView.findViewById(R.id.status);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }
    }
}
