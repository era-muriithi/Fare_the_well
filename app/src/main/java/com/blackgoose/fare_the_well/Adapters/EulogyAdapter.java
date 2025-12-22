package com.blackgoose.fare_the_well.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.blackgoose.fare_the_well.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class EulogyAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<String> images;
    private final OnImageClickListener listener;

    public interface OnImageClickListener {
        void onClick(int position);
    }

    public EulogyAdapter(Context context, ArrayList<String> images, OnImageClickListener listener) {
        this.context = context;
        this.images = images;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        ImageView img;

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_pager_image, parent, false);
        } else {
            view = convertView;
        }

        img = view.findViewById(R.id.pagerImage);

        Glide.with(context)
                .load(images.get(position))
                .placeholder(R.drawable.gallery)
                .centerCrop()
                .into(img);

        view.setOnClickListener(v -> {
            if (listener != null) listener.onClick(position);
        });

        return view;
    }
}
