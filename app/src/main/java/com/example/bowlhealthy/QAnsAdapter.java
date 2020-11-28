package com.example.bowlhealthy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class QAnsAdapter extends RecyclerView.Adapter<QAnsAdapter.ViewHolder> {

    List<QuestionAnswer> qaList;

    public QAnsAdapter(List<QuestionAnswer> qaList){
        this.qaList = qaList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mtvTitle,mtvAnswer;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            mtvTitle = itemView.findViewById(R.id.faq_title);
            mtvAnswer = itemView.findViewById(R.id.faq_answer);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    QuestionAnswer questionAnswer = qaList.get(getAdapterPosition());
                    questionAnswer.setExpandable(!questionAnswer.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }

    }

    @NonNull
    @Override
    public QAnsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QAnsAdapter.ViewHolder holder, int position) {
        holder.mtvTitle.setText(qaList.get(position).getQuestion());
        holder.mtvAnswer.setText(qaList.get(position).getAnswer());

        boolean isExpandable = qaList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return qaList.size();
    }
}
