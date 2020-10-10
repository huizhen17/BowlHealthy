package com.example.bowlhealthy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> implements Filterable {

    ArrayList<MenuDetail> menuDetails;
    ArrayList<MenuDetail> menuDetailsFull;
    Context context;

    public MenuAdapter(Context context,ArrayList<MenuDetail> menuDetails){
        this.context = context;
        this.menuDetails = menuDetails;
        //take a copy of menu Details
        menuDetailsFull = new ArrayList<>(menuDetails);
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
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,SingleMenu.class);
                intent.putExtra("image",menuDetails.get(position).getMenuImg());
                intent.putExtra("title",menuDetails.get(position).getMenuName());
                intent.putExtra("time",menuDetails.get(position).getTime());
                intent.putExtra("calories",menuDetails.get(position).getCalories());
                intent.putExtra("desc",menuDetails.get(position).getMenuDesc());
                intent.putExtra("price",menuDetails.get(position).getPrice());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return menuDetails.size();
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<MenuDetail> filterList = new ArrayList<>();

            if(charSequence == null || charSequence.length()==0){
                Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show();
                filterList.addAll(menuDetailsFull);
            }
            else{
                Toast.makeText(context,"Hello123",Toast.LENGTH_SHORT).show();
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(MenuDetail detail : menuDetailsFull){
                    if (detail.getMenuName().toLowerCase().contains(filterPattern)){
                        filterList.add(detail);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            menuDetails.clear();
            menuDetails.addAll((Collection<? extends MenuDetail>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
