package com.example.bowlhealthy;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    Context context;
    ArrayList<CartDetail> cartModel;
    private int itemCounter=1;
    private float price=0.0f,totalPrice = 0.0f;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID = mAuth.getCurrentUser().getUid();

    //Create constructor for cart adapter
    public CartAdapter(Context context,ArrayList<CartDetail> cartModel){
        this.context = context;
        this.cartModel = cartModel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mivCartImg, mivDeductCart, mivAddCart;
        TextView mtvCartTitle, mtvCartPrice, mtvCartQty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            mivCartImg = itemView.findViewById(R.id.ivCartBowl);
            mivDeductCart = itemView.findViewById(R.id.ivDeductItem);
            mivAddCart = itemView.findViewById(R.id.ivAddItem);
            mtvCartTitle = itemView.findViewById(R.id.tvCartTitle);
            mtvCartPrice = itemView.findViewById(R.id.tvCartPrice);
            mtvCartQty = itemView.findViewById(R.id.tvCartNo);

        }

    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_single_cart,parent,false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.ViewHolder holder, final int position) {
        holder.mivCartImg.setImageResource(cartModel.get(position).getCartImg());
        holder.mtvCartTitle.setText(cartModel.get(position).getCartMenu());
        holder.mtvCartPrice.setText(cartModel.get(position).getCartPrice());
        holder.mtvCartQty.setText(cartModel.get(position).getCartQty());

        //When user click on the add item
        holder.mivAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartModel.get(position).setCartQty(Integer.parseInt(cartModel.get(position)
                                                        .getCartQty())+1+"");
                savedItem(position);
            }
        });

        //When user click on the deduct item
        holder.mivDeductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartModel.get(position).setCartQty(Integer.parseInt(cartModel.get(position)
                                                        .getCartQty())-1+"");

                if(Integer.parseInt(cartModel.get(position).getCartQty())<1){
                    deleteItem(position); //delete item when quantity less than one
                }
                else{
                    savedItem(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartModel.size();
    }

    /*
        Function to update item into firebase
     */
    public void savedItem(int position){
        DocumentReference getMenuDB =  db.collection("userDetail").document(userID).
                collection("cartDetail").document(cartModel.get(position).getCartMenu());
        getMenuDB.update("cartQty",cartModel.get(position).getCartQty())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Error. Please try again later.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
        Function to delete item from firebase
     */
    public void deleteItem(int position){
        DocumentReference getMenuDB =  db.collection("userDetail").document(userID).collection("cartDetail").document(cartModel.get(position).getCartMenu());
        cartModel.clear(); //to prevent duplicate list
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
}
