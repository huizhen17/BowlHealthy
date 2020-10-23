package com.example.bowlhealthy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SingleHistoryAdapter extends RecyclerView.Adapter<SingleHistoryAdapter.ViewHolder> {

    Context context;
    ArrayList<ReceiptDetail> repModel;
    private HistoryOnClick mHistoryOnClick;

    //Create constructor for cart adapter
    public SingleHistoryAdapter(Context context, ArrayList<ReceiptDetail> repModel, HistoryOnClick mHistoryOnClick){
        this.context = context;
        this.repModel = repModel;
        this.mHistoryOnClick = mHistoryOnClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        HistoryOnClick historyOnClick;
        TextView mtvRepNo,mtvRepDate,mtvRepAmt;

        public ViewHolder(@NonNull View itemView,HistoryOnClick historyOnClick) {
            super(itemView);
            //Assign variable
            mtvRepNo = itemView.findViewById(R.id.tvSingleRepNo);
            mtvRepDate = itemView.findViewById(R.id.tvSingleRepDate);
            mtvRepAmt = itemView.findViewById(R.id.tvSingleRepAmount);
            this.historyOnClick = historyOnClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            historyOnClick.HistoryOnClick(getAdapterPosition());
        }
    }


    @NonNull
    @Override
    public SingleHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_myorder,parent,false);
        return new SingleHistoryAdapter.ViewHolder(view,mHistoryOnClick);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleHistoryAdapter.ViewHolder holder, int position) {
        holder.mtvRepNo.setText(repModel.get(position).getRepID());
        holder.mtvRepDate.setText(repModel.get(position).getRepDate());
        holder.mtvRepAmt.setText(repModel.get(position).getRepTotal());
    }

    @Override
    public int getItemCount() {
        return repModel.size();
    }

    //Create interface when item on click
    public interface HistoryOnClick{
        void HistoryOnClick(int position);
    }
}
