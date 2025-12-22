package com.blackgoose.fare_the_well.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.PreviewActivity;
import com.bumptech.glide.Glide;
import com.blackgoose.fare_the_well.R;

import java.util.List;

public class PreviewImagesAdapter extends RecyclerView.Adapter<PreviewImagesAdapter.PreviewViewHolder> {

    private List<String> imageList;
    private Context context;

    public PreviewImagesAdapter(Context context, List<String> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public PreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pager_image, parent, false);
        return new PreviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviewViewHolder holder, int position) {
        String imageUrl = imageList.get(position);

        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            holder.imageView.setImageResource(R.drawable.gallery);
            return;
        }

        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.gallery)
                .error(R.drawable.gallery)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList != null ? imageList.size() : 0;
    }

    public static class PreviewViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public PreviewViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pagerImage);
        }
    }
}
