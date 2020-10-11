package com.example.bowlhealthy;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.xml.datatype.DatatypeFactory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    ArrayList<MenuDetail> favModels;
    Context context;

    public FavAdapter(Context context,ArrayList<MenuDetail> favModels){
        this.context = context;
        this.favModels = favModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variable
        ImageView ivBowl;
        ImageView ivFavIcon;
        TextView title;
        TextView duration;
        TextView price;
        RelativeLayout mrelativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign Variable
            ivBowl = itemView.findViewById(R.id.ivFavBowl);
            ivFavIcon = itemView.findViewById(R.id.ivSaved);
            title = itemView.findViewById(R.id.tvFavTitle);
            duration = itemView.findViewById(R.id.tvFavClock);
            price = itemView.findViewById(R.id.tvFavPrice);
            mrelativeLayout = itemView.findViewById(R.id.mrelativeLayout);
        }
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_fav,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavAdapter.ViewHolder holder, final int position) {
        holder.ivBowl.setImageResource(favModels.get(position).getMenuImg());
        holder.title.setText(favModels.get(position).getMenuName());
        holder.duration.setText(favModels.get(position).getTime());
        holder.price.setText(favModels.get(position).getPrice());

        holder.mrelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Place the content back to Single Menu page
                Intent intent = new Intent(context,SingleMenu.class);
                intent.putExtra("image",favModels.get(position).getMenuImg());
                intent.putExtra("title",favModels.get(position).getMenuName());
                intent.putExtra("time",favModels.get(position).getTime());
                intent.putExtra("calories",favModels.get(position).getCalories());
                intent.putExtra("desc",favModels.get(position).getMenuDesc());
                intent.putExtra("price",favModels.get(position).getPrice());
                context.startActivity(intent);
            }
        });

        holder.ivFavIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favModels.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return favModels.size();
    }
}
