package com.blackgoose.fare_the_well.Adapters;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class FullScreenAdapter extends RecyclerView.Adapter<FullScreenAdapter.ViewHolder> {

    private Context context;
    private List<String> images;

    public FullScreenAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fullscreen_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = images.get(position);
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.gallery)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fullImage);
        }
    }
}

