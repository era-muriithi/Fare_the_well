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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class UserEulogyAdapter extends FirebaseRecyclerAdapter<EulogyModel, UserEulogyAdapter.myViewHolder> {
    ProgressBar progressBar;

    public UserEulogyAdapter (FirebaseRecyclerOptions<EulogyModel> options) {
        super(options);
    }
    @NonNull
    public UserEulogyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_eulogy_custom_item,parent, false);
        return new myViewHolder(view);
    }

    protected void onBindViewHolder(@NonNull UserEulogyAdapter.myViewHolder holder, int position, @NonNull EulogyModel model) {

        holder.deceased_Fname.setText(model.getDeceasedFname());
        holder.deceased_Sname.setText(model.getDeceasedSname());
        holder.deceased_Lname.setText(model.getDeceasedLname());
        holder.deceased_dob.setText(model.getDateOfBirth());
        holder.deceased_dod.setText(model.getDateOfDeath());

        Glide.with(holder.deceased_img.getContext())
                .load(model.getDeceasedPicture())
                .placeholder(R.drawable.person_24)
                .error(R.drawable.person_24)
                .into(holder.deceased_img);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, UserEulogyDetailActivity.class);
                intent.putExtra("deceasedImage", model.getDeceasedPicture());
                intent.putExtra("deceasedFname", model.getDeceasedFname());
                intent.putExtra("deceasedSname", model.getDeceasedSname());
                intent.putExtra("deceasedLname", model.getDeceasedLname());
                intent.putExtra( "deceaseDob", model.getDateOfBirth());
                intent.putExtra("deceasedDod", model.getDateOfDeath());
                intent.putExtra("burialLocation", model.getBurialLocation());
                intent.putExtra("deceasedEarlylife", model.getEarlylifeBiography());
                intent.putExtra("deceasedEducation", model.getEducationBiography());
                intent.putExtra("deceasedWork", model.getWorkBiography());
                intent.putExtra("deceasedFamily", model.getFamilyBiography());
                intent.putExtra("deceaseFinalMoments", model.getFinalMoments());
                intent.putExtra("Key", model.getKey());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.deceased_Fname.getContext());
                builder.setTitle("Are you sure?");
                builder.setMessage("Deleted data can't be undone");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("Eulogies")
                                .child(getRef(position).getKey()).removeValue();


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

