package com.example.bowlhealthy;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import javax.xml.datatype.DatatypeFactory;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {

    ArrayList<MenuDetail> favModels;
    Context context;
    private FavOnClick mfavOnClick;

    public FavAdapter(Context context,ArrayList<MenuDetail> favModels,FavOnClick mfavOnClick){
        this.context = context;
        this.favModels = favModels;
        this.mfavOnClick = mfavOnClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Initialize variable
        ImageView ivBowl;
        ImageView ivFavIcon;
        TextView title;
        TextView duration;
        TextView price;
        FavOnClick favOnClick;

        public ViewHolder(@NonNull View itemView,FavOnClick favOnClick) {
            super(itemView);
            //Assign Variable
            ivBowl = itemView.findViewById(R.id.ivFavBowl);
            ivFavIcon = itemView.findViewById(R.id.ivSaved);
            title = itemView.findViewById(R.id.tvFavTitle);
            duration = itemView.findViewById(R.id.tvFavClock);
            price = itemView.findViewById(R.id.tvFavPrice);
            this.favOnClick = favOnClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            favOnClick.FavOnClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_fav,parent,false);
        return new ViewHolder(view,mfavOnClick);
    }

    @Override
    public void onBindViewHolder(@NonNull final FavAdapter.ViewHolder holder, final int position) {
        holder.ivBowl.setImageResource(favModels.get(position).getMenuImg());
        holder.title.setText(favModels.get(position).getMenuName());
        holder.duration.setText(favModels.get(position).getTime());
        holder.price.setText(favModels.get(position).getPrice());

        //Fav icon clicked
        holder.ivFavIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                String userID = mAuth.getCurrentUser().getUid();

                DocumentReference getMenuDB =  db.collection("userDetail").document(userID).collection("favDetail").document(favModels.get(position).getMenuName());
                favModels.clear(); //to prevent duplicate list
                getMenuDB.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context,"Item removed from your list.",Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,"Error. Please try again later.",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return favModels.size();
    }

    //Create interface when item on click
    public interface FavOnClick{
        void FavOnClick(int position);
    }
}
