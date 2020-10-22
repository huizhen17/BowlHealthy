package com.example.bowlhealthy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    Context context;
    ArrayList<CartDetail> repModel;

    //Create constructor for cart adapter
    public OrderItemAdapter(Context context, ArrayList<CartDetail> repModel){
        this.context = context;
        this.repModel = repModel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mtvOrderName,mtvOrderQty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            mtvOrderName = itemView.findViewById(R.id.tvOrderItemName);
            mtvOrderQty = itemView.findViewById(R.id.tvOrderItemQty);
        }

    }

    @NonNull
    @Override
    public OrderItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_info,parent,false);
        return new OrderItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.ViewHolder holder, int position) {
        holder.mtvOrderName.setText(repModel.get(position).getCartMenu());
        holder.mtvOrderQty.setText(repModel.get(position).getCartQty());
    }

    @Override
    public int getItemCount() {
        return repModel.size();
    }
}
