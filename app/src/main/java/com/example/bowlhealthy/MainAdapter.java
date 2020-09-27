package com.example.bowlhealthy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<MainMenuItem> mainModels;
    Context context;

    public MainAdapter(Context context,ArrayList<MainMenuItem> mainModels){
        this.context = context;
        this.mainModels = mainModels;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_menu_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        //Set image and text
        holder.imageView.setImageResource(mainModels.get(position).getImage());
        holder.title.setText(mainModels.get(position).getTitle());
        holder.desc.setText(mainModels.get(position).getDesc());

    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variable
        ImageView imageView;
        TextView title;
        TextView desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign Variable
            imageView = itemView.findViewById(R.id.iv_menu_bowl);
            title = itemView.findViewById(R.id.tv_bowl_title);
            desc = itemView.findViewById(R.id.tv_bowl_desc);
        }
    }
}
