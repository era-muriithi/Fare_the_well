//package com.blackgoose.fare_the_well.Adapters;
//
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//import com.blackgoose.fare_the_well.Models.EulogyModel;
//import com.blackgoose.fare_the_well.R;
//import com.blackgoose.fare_the_well.UserEulogyDetailActivity;
//import com.bumptech.glide.Glide;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.List;
//
//public class UserEulogyAdapter extends FirebaseRecyclerAdapter<EulogyModel, UserEulogyAdapter.myViewHolder> {
//    ProgressBar progressBar;
//
//    public UserEulogyAdapter (FirebaseRecyclerOptions<EulogyModel> options) {
//        super(options);
//    }
//    @NonNull
//    public UserEulogyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_eulogy_custom_item,parent, false);
//        return new myViewHolder(view);
//    }
//
//    protected void onBindViewHolder(@NonNull UserEulogyAdapter.myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull EulogyModel model) {
//
//        holder.deceased_Fname.setText(model.getDeceasedFname());
//        holder.deceased_Sname.setText(model.getDeceasedSname());
//        holder.deceased_Lname.setText(model.getDeceasedLname());
//        holder.deceased_dob.setText(model.getDateOfBirth());
//        holder.deceased_dod.setText(model.getDateOfDeath());
//        List<String> pictures = model.getDeceasedPictures();
//        if (pictures != null && !pictures.isEmpty()) {
//            Glide.with(holder.deceased_img.getContext())
//                    .load(pictures.get(0))  // Load the first image
//                    .placeholder(R.drawable.person_24)
//                    .error(R.drawable.person_24)
//                    .into(holder.deceased_img);
//        } else {
//            // Handle the case where there are no pictures
//            holder.deceased_img.setImageResource(R.drawable.person_24);
//        }
////        Glide.with(holder.deceased_img.getContext())
////                .load(model.getDeceasedPicture())
////                .placeholder(R.drawable.person_24)
////                .error(R.drawable.person_24)
////                .into(holder.deceased_img);
//
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Context context = view.getContext();
//                Intent intent = new Intent(context, UserEulogyDetailActivity.class);
////                intent.putExtra("deceasedImage", model.getDeceasedPicture());
//                intent.putExtra("deceasedFname", model.getDeceasedFname());
//                intent.putExtra("deceasedSname", model.getDeceasedSname());
//                intent.putExtra("deceasedLname", model.getDeceasedLname());
//                intent.putExtra( "deceaseDob", model.getDateOfBirth());
//                intent.putExtra("deceasedDod", model.getDateOfDeath());
//                intent.putExtra("burialLocation", model.getBurialLocation());
//                intent.putExtra("deceasedEarlylife", model.getEarlylifeBiography());
//                intent.putExtra("deceasedEducation", model.getEducationBiography());
//                intent.putExtra("deceasedWork", model.getWorkBiography());
//                intent.putExtra("deceasedFamily", model.getFamilyBiography());
//                intent.putExtra("deceaseFinalMoments", model.getFinalMoments());
//                intent.putExtra("Key", model.getKey());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(holder.deceased_Fname.getContext());
//                builder.setTitle("Are you sure?");
//                builder.setMessage("Deleted data can't be undone");
//
//                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        // Show a progress dialog or some sort of loading indicator
//                        progressBar.setVisibility(View.VISIBLE);
//
//                        // Get the reference to the specific eulogy entry in Firebase
//                        FirebaseDatabase.getInstance().getReference().child("Eulogies")
//                                .child(getRef(position).getKey()).removeValue()
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        // Hide progress bar and show a success message
//                                        progressBar.setVisibility(View.GONE);
//                                        Toast.makeText(holder.deceased_Fname.getContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        // Hide progress bar and show an error message
//                                        progressBar.setVisibility(View.GONE);
//                                        Toast.makeText(holder.deceased_Fname.getContext(), "Deletion failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                                    }
//                                });
//                    }
//                });
//
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(holder.deceased_Fname.getContext(), "Cancelled", Toast.LENGTH_LONG).show();
//                    }
//                });
//
//                builder.show();
//            }
//        });
//
////        holder.delete.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                AlertDialog.Builder builder = new AlertDialog.Builder(holder.deceased_Fname.getContext());
////                builder.setTitle("Are you sure?");
////                builder.setMessage("Deleted data can't be undone");
////
////                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////
////                        FirebaseDatabase.getInstance().getReference().child("Eulogies")
////                                .child(getRef(position).getKey()).removeValue();
////
////
////                    }
////                });
////                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////                        Toast.makeText(holder.deceased_Fname.getContext(), "Cancelled", Toast.LENGTH_LONG).show();
////                    }
////                });
////                builder.show();
////            }
////        });
//
//
//
//    }
//
//    static class myViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView deceased_img;
//        Button delete;
//        TextView deceased_Fname, deceased_Sname, deceased_Lname, deceased_dob, deceased_dod, burial_location, deceased_earlyLife, deceased_education, deceased_work, deceased_family, deceased_finalMoments;
//
//        CardView cardView;
//        public myViewHolder(View itemView) {
//            super(itemView);
//            deceased_img = itemView.findViewById(R.id.img);
//            deceased_Fname = itemView.findViewById(R.id.Fname);
//            deceased_Sname = itemView.findViewById(R.id.Sname);
//            deceased_Lname = itemView.findViewById(R.id.Lname);
//            deceased_dob = itemView.findViewById(R.id.born_dates);
//            deceased_dod = itemView.findViewById(R.id.death_date);
//            burial_location = itemView.findViewById(R.id.burial_location);
//            deceased_earlyLife = itemView.findViewById(R.id.earlylife_biography);
//            deceased_education = itemView.findViewById(R.id.education_biography);
//            deceased_work = itemView.findViewById(R.id.work_biography);
//            deceased_family = itemView.findViewById(R.id.family_biography);
//            deceased_finalMoments = itemView.findViewById(R.id.final_biography);
//            delete = itemView.findViewById(R.id.delete_btn);
//            cardView =itemView.findViewById(R.id.listCard);
//
//        }
//    }
//    public void onDataChanged() {
//        if (progressBar != null) {
//            progressBar.setVisibility(View.GONE);
//        }
//    }
//
//}
//
package com.blackgoose.fare_the_well.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.EulogyDetailsActivity;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.blackgoose.fare_the_well.R;
import com.blackgoose.fare_the_well.UserEulogyDetailActivity;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Objects;

public class UserEulogyAdapter extends FirebaseRecyclerAdapter<EulogyModel, UserEulogyAdapter.myViewHolder> {

    public UserEulogyAdapter(FirebaseRecyclerOptions<EulogyModel> options) {
        super(options);
    }

    @NonNull
    public UserEulogyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_eulogy_custom_item, parent, false);
        return new myViewHolder(view);
    }

    protected void onBindViewHolder(@NonNull UserEulogyAdapter.myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull EulogyModel model) {
        holder.deceased_Fname.setText(model.getDeceasedFname());
        holder.deceased_Sname.setText(model.getDeceasedSname());
        holder.deceased_Lname.setText(model.getDeceasedLname());
        holder.deceased_dob.setText(model.getDateOfBirth());
        holder.deceased_dod.setText(model.getDateOfDeath());

        List<String> pictures = model.getDeceasedPictures();
        if (pictures != null && !pictures.isEmpty()) {
            Glide.with(holder.deceased_img.getContext())
                    .load(pictures.get(0))  // Load the first image
                    .placeholder(R.drawable.person_24)
                    .error(R.drawable.person_24)
                    .into(holder.deceased_img);
        } else {
            holder.deceased_img.setImageResource(R.drawable.person_24);
        }
        holder.cardView.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, UserEulogyDetailActivity.class);
            intent.putExtra("user1", model.getUserUid());



            context.startActivity(intent);
        });
//        holder.cardView.setOnClickListener(view -> {
//            Context context = view.getContext();
//            Intent intent = new Intent(context, UserEulogyDetailActivity.class);
//            intent.putExtra("deceasedFname", model.getDeceasedFname());
//            intent.putExtra("deceasedSname", model.getDeceasedSname());
//            intent.putExtra("deceasedLname", model.getDeceasedLname());
//            intent.putExtra("deceaseDob", model.getDateOfBirth());
//            intent.putExtra("deceasedDod", model.getDateOfDeath());
//            intent.putExtra("burialLocation", model.getBurialLocation());
//            intent.putExtra("deceasedEarlylife", model.getEarlylifeBiography());
//            intent.putExtra("deceasedEducation", model.getEducationBiography());
//            intent.putExtra("deceasedWork", model.getWorkBiography());
//            intent.putExtra("deceasedFamily", model.getFamilyBiography());
//            intent.putExtra("deceaseFinalMoments", model.getFinalMoments());
//            intent.putExtra("Key", model.getKey());
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        });

        holder.delete.setOnClickListener(view -> {

            String userID  = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.deceased_Fname.getContext());
            builder.setTitle("Are you sure?");
            builder.setMessage("Deleted data can't be undone");
//
//            builder.setPositiveButton("Delete", (dialogInterface, i) -> {
//                FirebaseDatabase.getInstance().getReference().child("Eulogies").child(userID)
//                        .child(getRef(position).getKey()).removeValue();
//            });

            builder.setPositiveButton("Delete", (dialogInterface, i) -> {
                String key = getRef(position).getKey();
                if (key != null && getSnapshots().getSnapshot(position).exists()) {
                    FirebaseDatabase.getInstance().getReference().child("Eulogies").child(userID)
                            .child(key).removeValue();
                } else {
                    Toast.makeText(holder.deceased_Fname.getContext(), "Item no longer exists.", Toast.LENGTH_SHORT).show();
                }
            });


            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                Toast.makeText(holder.deceased_Fname.getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            });

            builder.show();
        });
    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        ImageView deceased_img;
        Button delete;
        TextView deceased_Fname, deceased_Sname, deceased_Lname, deceased_dob, deceased_dod;
        CardView cardView;

        public myViewHolder(View itemView) {
            super(itemView);
            deceased_img = itemView.findViewById(R.id.img);
            deceased_Fname = itemView.findViewById(R.id.Fname);
            deceased_Sname = itemView.findViewById(R.id.Sname);
            deceased_Lname = itemView.findViewById(R.id.Lname);
            deceased_dob = itemView.findViewById(R.id.born_dates);
            deceased_dod = itemView.findViewById(R.id.death_date);
            delete = itemView.findViewById(R.id.delete_btn);
            cardView = itemView.findViewById(R.id.listCard);
        }
    }
}
