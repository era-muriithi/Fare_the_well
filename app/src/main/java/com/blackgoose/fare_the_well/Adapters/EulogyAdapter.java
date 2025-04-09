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

import java.util.ArrayList;
import java.util.List;

public class EulogyAdapter extends RecyclerView.Adapter<EulogyAdapter.myViewHolder> {
    private Context context;
    private List<EulogyModel> list;

    public EulogyAdapter (Context context, List<EulogyModel> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    public EulogyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.eulogy_custom_item,parent, false);
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

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, EulogyDetailsActivity.class);

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
                intent.putExtra("eulogy", model);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
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

