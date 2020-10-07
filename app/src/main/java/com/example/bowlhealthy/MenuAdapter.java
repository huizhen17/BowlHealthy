package com.example.bowlhealthy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{

    ArrayList<MenuDetail> menuDetails;
    Context context;

    public MenuAdapter(Context context,ArrayList<MenuDetail> menuDetails){
        this.context = context;
        this.menuDetails = menuDetails;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Initialize variable
        ImageView ivBowl;
        TextView title;
        TextView ingredient;
        TextView duration;
        TextView price;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign Variable
            ivBowl = itemView.findViewById(R.id.ivBowl);
            title = itemView.findViewById(R.id.tvmenuTitle);
            ingredient = itemView.findViewById(R.id.tvMenuIng);
            duration = itemView.findViewById(R.id.tvMins);
            price = itemView.findViewById(R.id.tvPrice);
            relativeLayout = itemView.findViewById(R.id.rlMenu);
        }
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, final int position) {
        holder.ivBowl.setImageResource(menuDetails.get(position).getMenuImg());
        holder.title.setText(menuDetails.get(position).getMenuName());
        holder.ingredient.setText(menuDetails.get(position).getIngredient());
        holder.duration.setText(menuDetails.get(position).getTime());
        holder.price.setText(menuDetails.get(position).getPrice());

        //Set onclick listener

        /*holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SingleMenu.class);
                intent.putExtra("title",menuDetails.get(position).getMenuName());
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return menuDetails.size();
    }


}
