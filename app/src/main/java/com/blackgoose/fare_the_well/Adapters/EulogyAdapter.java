package com.blackgoose.fare_the_well.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.EulogyDetailsActivity;
import com.blackgoose.fare_the_well.Models.EulogyModel;
import com.blackgoose.fare_the_well.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class EulogyAdapter extends FirebaseRecyclerAdapter<EulogyModel, EulogyAdapter.myViewHolder> {

    public EulogyAdapter (FirebaseRecyclerOptions<EulogyModel> options) {
        super(options);
    }
    @NonNull
    public EulogyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eulogy_custom_item,parent, false);
        return new myViewHolder(view);
    }

    protected void onBindViewHolder(@NonNull EulogyAdapter.myViewHolder holder, int position, @NonNull EulogyModel model) {

        holder.deceased_Fname.setText(model.getDeceasedFname());
        holder.deceased_Sname.setText(model.getDeceasedSname());
        holder.deceased_Lname.setText(model.getDeceasedLname());
        holder.deceased_dob.setText(model.getDateOfBirth());
        holder.deceased_dod.setText(model.getDateOfDeath());

//        Glide.with(holder.deceased_img.getContext())
//                .load(model.getDeceasedPicture())
//                .placeholder(R.drawable.person_24)
//                .error(R.drawable.person_24)
//                .into(holder.deceased_img);

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
            Intent intent = new Intent(context, EulogyDetailsActivity.class);
            intent.putExtra("user1", model.getUserUid());



            context.startActivity(intent);
        });

    }

    static class myViewHolder extends RecyclerView.ViewHolder {

        ImageView deceased_img;
        TextView deceased_Fname, deceased_Sname, deceased_Lname, deceased_dob, deceased_dod, burial_location, deceased_earlyLife, deceased_education, deceased_work, deceased_family, deceased_finalMoments;
        ProgressBar progressBar;
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
            cardView =itemView.findViewById(R.id.listCard);

        }
    }

}

