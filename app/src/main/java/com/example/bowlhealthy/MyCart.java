package com.example.bowlhealthy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyCart extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<CartDetail> cartDetails = new ArrayList<>();
    CartAdapter cartAdapter;
    RecyclerView mRecyclerview;
    ImageView mivEmptyCart, mivCartBg;
    TextView mtvEmptyTitle, mtvEmptyDesc, mtvCartRm, mtvTextSubtotal, mtvSubtotal;
    Button mbtnCheckout,mbtnBackToMenu;
    String userID;
    int counter;
    String subTotal ="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        //Assign Variable
        mivEmptyCart = findViewById(R.id.ivEmptyCart);
        mivCartBg = findViewById(R.id.ivCartBg);
        mtvEmptyTitle = findViewById(R.id.tvEmptyCartTitle);
        mtvEmptyDesc = findViewById(R.id.tvEmptyCartDesc);
        mtvCartRm = findViewById(R.id.textRM);
        mtvSubtotal = findViewById(R.id.tvSubtotal);
        mtvTextSubtotal = findViewById(R.id.textSubtotal);
        mbtnCheckout = findViewById(R.id.btnCheckOut);
        mbtnBackToMenu = findViewById(R.id.btnBackToMenu);

        mivEmptyCart.setVisibility(View.VISIBLE);
        mtvEmptyTitle.setVisibility(View.VISIBLE);
        mtvEmptyDesc.setVisibility(View.VISIBLE);
        mbtnBackToMenu.setVisibility(View.VISIBLE);
        mivCartBg.setVisibility(View.INVISIBLE);
        mtvCartRm.setVisibility(View.INVISIBLE);
        mtvSubtotal.setVisibility(View.INVISIBLE);
        mtvTextSubtotal.setVisibility(View.INVISIBLE);
        mbtnCheckout.setVisibility(View.INVISIBLE);

        mbtnBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyCart.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        userID = mAuth.getCurrentUser().getUid();

        //Get instant update
        CollectionReference getMenuDB =  db.collection("userDetail").document(userID).collection("cartDetail");
        getMenuDB.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error==null) {
                    if (value.isEmpty()){
                        mivEmptyCart.setVisibility(View.VISIBLE);
                        mtvEmptyTitle.setVisibility(View.VISIBLE);
                        mtvEmptyDesc.setVisibility(View.VISIBLE);
                        mbtnBackToMenu.setVisibility(View.VISIBLE);
                        mivCartBg.setVisibility(View.INVISIBLE);
                        mtvCartRm.setVisibility(View.INVISIBLE);
                        mtvSubtotal.setVisibility(View.INVISIBLE);
                        mtvTextSubtotal.setVisibility(View.INVISIBLE);
                        mbtnCheckout.setVisibility(View.INVISIBLE);
                    }else {
                        subTotal ="0";
                        cartDetails.clear();
                        for (QueryDocumentSnapshot document : value) {
                            retrieveQuery(document.toObject(CartDetail.class), value.size());
                        }
                    }
                }else
                    Toast.makeText(getApplicationContext(),"Fail to retrieve data.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*
        Function to retrieve query from collection
     */
    public void retrieveQuery(CartDetail cartDetail1, int collectionSize){
        cartDetails.add(cartDetail1);

        //Calculate Subtotal
        double tempo = Double.parseDouble(cartDetail1.getCartPrice())*Integer.parseInt(cartDetail1.getCartQty());
        subTotal = String.format("%.2f",Double.parseDouble(subTotal)+tempo);
        counter = collectionSize;

        if (cartDetails.size()==counter){
            setRV();
            mtvSubtotal.setText(subTotal);
            mivCartBg.setVisibility(View.VISIBLE);
            mtvCartRm.setVisibility(View.VISIBLE);
            mtvSubtotal.setVisibility(View.VISIBLE);
            mtvTextSubtotal.setVisibility(View.VISIBLE);
            mbtnCheckout.setVisibility(View.VISIBLE);
            mivEmptyCart.setVisibility(View.INVISIBLE);
            mtvEmptyTitle.setVisibility(View.INVISIBLE);
            mtvEmptyDesc.setVisibility(View.INVISIBLE);
            mbtnBackToMenu.setVisibility(View.INVISIBLE);
        }
    }

    /*
        Function to set recycler view
     */
    public void setRV(){
        //set up recycler view
        mRecyclerview = findViewById(R.id.cartRecyclerView);
        cartAdapter = new CartAdapter(this, cartDetails);
        cartAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(layoutManager);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerview);
        mRecyclerview.setAdapter(cartAdapter);
    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }

    public void btnOnClick_checkOut(View view) {
        Intent i = new Intent(MyCart.this, CheckOut.class);
        i.putExtra("subtotal",subTotal);
        i.putParcelableArrayListExtra("cartList",cartDetails);
        startActivity(i);
    }

    /*
        Function to perform swipe left to delete the item in cart
     */
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mivEmptyCart.setVisibility(View.VISIBLE);
            mtvEmptyTitle.setVisibility(View.VISIBLE);
            mtvEmptyDesc.setVisibility(View.VISIBLE);
            mbtnBackToMenu.setVisibility(View.VISIBLE);
            mivCartBg.setVisibility(View.INVISIBLE);
            mtvCartRm.setVisibility(View.INVISIBLE);
            mtvSubtotal.setVisibility(View.INVISIBLE);
            mtvTextSubtotal.setVisibility(View.INVISIBLE);
            mbtnCheckout.setVisibility(View.INVISIBLE);
            cartAdapter.deleteItem(viewHolder.getAdapterPosition());
        }
    };

    public void btnHelp_onClick(View view) {
        Intent i = new Intent(MyCart.this,FAQ.class);
        startActivity(i);
    }
}
