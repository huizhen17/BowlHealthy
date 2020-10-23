package com.example.bowlhealthy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderHistory extends AppCompatActivity implements SingleHistoryAdapter.HistoryOnClick{

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView mMyHistoryRV;
    SingleHistoryAdapter singleHistoryAdapter;
    ArrayList<ReceiptDetail> repList = new ArrayList<>();
    String userID;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        userID = mAuth.getCurrentUser().getUid();
        //Get instant update
        CollectionReference getMenuDB =  db.collection("userDetail").document(userID).collection("orderDetail");
        getMenuDB.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error==null) {
                    if (value.isEmpty()){
                        //TODO::Set Empty View in xml
                    }else {
                        for (QueryDocumentSnapshot document : value) {
                            retrieveQuery(document.toObject(ReceiptDetail.class), value.size());
                        }
                    }

                }else
                    Toast.makeText(getApplicationContext(),"Fail to retrieve data.",Toast.LENGTH_SHORT).show();
            }
        });


        //TODO::Adapter
    }

    public void retrieveQuery(ReceiptDetail repDetail1, int collectionSize){
        repList.add(repDetail1);
        counter = collectionSize;
        if (repList.size()==counter){
            setRV();
            //TODO::Set Empty View in xml
        }
    }

    /*
        set up recycler view
     */
    public void setRV(){
        mMyHistoryRV = findViewById(R.id.myorderRV);
        singleHistoryAdapter = new SingleHistoryAdapter(this, repList,this);
        singleHistoryAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mMyHistoryRV.setLayoutManager(layoutManager);
        mMyHistoryRV.setAdapter(singleHistoryAdapter);
    }


    public void btnHelp_onClick(View view) {
    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }

    @Override
    public void HistoryOnClick(int position) {
        Intent i = new Intent(OrderHistory.this,SingleHistory.class);
        i.putExtra("receiptNo",repList.get(position).getRepID());
        i.putExtra("name",repList.get(position).getRepName());
        i.putExtra("phone",repList.get(position).getRepPhone());
        i.putExtra("date",repList.get(position).getRepDate());
        i.putExtra("time",repList.get(position).getRepTime());
        i.putExtra("subtotal",repList.get(position).getRepSubtotal());
        i.putExtra("amount",repList.get(position).getRepTotal());
        //TODO::Passing the cart Detail in order Detail
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
