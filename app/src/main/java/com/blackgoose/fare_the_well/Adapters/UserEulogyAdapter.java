package com.blackgoose.fare_the_well.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.blackgoose.fare_the_well.R;
import com.blackgoose.fare_the_well.UserEulogyDetailActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserEulogyAdapter extends RecyclerView.Adapter<UserEulogyAdapter.myViewHolder> {
    private Context context;
    private List<EulogyModel> list;
    ProgressBar progressBar;

    public UserEulogyAdapter (Context context, List<EulogyModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    public UserEulogyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_eulogy_custom_item,parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        EulogyModel model = list.get(position);
        holder.deceased_Fname.setText(model.getFirstName());
        holder.deceased_Sname.setText(model.getSecondName());
        holder.deceased_Lname.setText(model.getLastName());
        holder.deceased_dob.setText(model.getDateOfBirth());
        holder.deceased_dod.setText(model.getPassingOnDate());

        if (model.getImageUrls() != null && !model.getImageUrls().isEmpty()) {
            Glide.with(context).load(model.getImageUrls().get(0)).into(holder.deceased_img);
        }
        DatabaseReference eulogiesRef = FirebaseDatabase.getInstance().getReference().child("Eulogies");


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, UserEulogyDetailActivity.class);
                intent.putStringArrayListExtra("deceasedImage", new ArrayList<>(model.getImageUrls()));
                intent.putExtra("deceasedFname", model.getFirstName());
                intent.putExtra("deceasedSname", model.getSecondName());
                intent.putExtra("deceasedLname", model.getLastName());
                intent.putExtra( "deceaseDob", model.getDateOfBirth());
                intent.putExtra("deceasedDod", model.getPassingOnDate());
                intent.putExtra("burialLocation", model.getBurialLocation());
                intent.putExtra("deceasedEarlylife", model.getEarly());
                intent.putExtra("deceasedEducation", model.getEducation());
                intent.putExtra("deceasedWork", model.getWork());
                intent.putExtra("deceasedFamily", model.getFamily());
                intent.putExtra("deceaseFinalMoments", model.getFinalMoment());
                intent.putExtra("Key", model.getKey());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            String eulogyKey = model.getKey();
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.deceased_Fname.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be undone");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (eulogyKey != null) {
                            eulogiesRef.child(eulogyKey).removeValue()
                                    .addOnSuccessListener(aVoid ->
                                            Toast.makeText(context, "Eulogy deleted successfully", Toast.LENGTH_SHORT).show())
                                    .addOnFailureListener(e ->
                                            Toast.makeText(context, "Failed to delete: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        } else {
                            Toast.makeText(context, "Error: Eulogy key not found", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.deceased_Fname.getContext(), "Cancelled", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    static class myViewHolder extends RecyclerView.ViewHolder {

        ImageView deceased_img;
        Button delete;
        TextView deceased_Fname, deceased_Sname, deceased_Lname, deceased_dob, deceased_dod, burial_location, deceased_earlyLife, deceased_education, deceased_work, deceased_family, deceased_finalMoments;

        CardView cardView;
        public myViewHolder(View itemView) {
            super(itemView);
            deceased_img = itemView.findViewById(R.id.img);
            deceased_Fname = itemView.findViewById(R.id.Fname);
            deceased_Sname = itemView.findViewById(R.id.Sname);
            deceased_Lname = itemView.findViewById(R.id.Lname);
            deceased_dob = itemView.findViewById(R.id.born_dates);
            deceased_dod = itemView.findViewById(R.id.death_date);
            burial_location = itemView.findViewById(R.id.burial_location);
            deceased_earlyLife = itemView.findViewById(R.id.earlylife_biography);
            deceased_education = itemView.findViewById(R.id.education_biography);
            deceased_work = itemView.findViewById(R.id.work_biography);
            deceased_family = itemView.findViewById(R.id.family_biography);
            deceased_finalMoments = itemView.findViewById(R.id.final_biography);
            delete = itemView.findViewById(R.id.delete_btn);
            cardView =itemView.findViewById(R.id.listCard);

        }
    }
    public void onDataChanged() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

}

