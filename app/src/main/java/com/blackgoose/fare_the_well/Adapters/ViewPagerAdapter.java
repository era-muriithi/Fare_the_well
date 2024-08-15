//package com.blackgoose.fare_the_well.Adapters;
//
//import android.content.Context;
//import android.net.Uri;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//
//import androidx.annotation.NonNull;
//import androidx.viewpager.widget.PagerAdapter;
//
//import com.blackgoose.fare_the_well.R;
//
//import java.util.ArrayList;
//
//public class ViewPagerAdapter  extends PagerAdapter {
//    private Context context;
//    ArrayList<Uri> uriArrayList;
//    LayoutInflater layoutInflater;
//
//    public ViewPagerAdapter(Context context, ArrayList<Uri> uriArrayList) {
//        this.context = context;
//        this.uriArrayList = uriArrayList;
//    }
//
//
//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull View container, int position) {
//        View view= layoutInflater.inflate(R.layout.image_layout, (ViewGroup) container,false);
//        ImageView i =view.findViewById(R.id.image_item);
//        i.setImageURI(uriArrayList.get(position));
//
//
//        ((ViewGroup) container).addView(view);
//        return view ;
//    }
//
//    @Override
//    public int getCount(){
//
//        return  uriArrayList.size();
//    }
//
//    @Override
//    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//
//        ((RelativeLayout)object).removeView(container);
//    }
//
//    @Override
//    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
//        return view ==object;
//    }
//}
//
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

import java.util.ArrayList;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Uri> uriArrayList;
    private LayoutInflater layoutInflater;

    public ViewPagerAdapter(Context context, ArrayList<Uri> uriArrayList) {
        this.context = context;
        this.uriArrayList = uriArrayList;
        this.layoutInflater = LayoutInflater.from(context); // Initialize layoutInflater here
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.image_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri imageUri = uriArrayList.get(position);
        holder.imageView.setImageURI(imageUri);
    }

    @Override
    public int getItemCount() {
        return uriArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
        }
    }
}
