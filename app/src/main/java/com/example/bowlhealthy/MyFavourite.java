package com.example.bowlhealthy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyFavourite extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView mRecyclerView;
    FavAdapter favAdapter;
    ArrayList<MenuDetail> favList = new ArrayList<>();
    String userID,duration,calories;
    int image,desc;
    String price;
    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);

        userID = mAuth.getCurrentUser().getUid();

        CollectionReference getMenuDB =  db.collection("userDetail").document(userID).collection("favDetail");

        getMenuDB.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot collectionReference = task.getResult();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                       retrieveQuery(document.toObject(MenuDetail.class),collectionReference.size());
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Fail to retrieve data.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void retrieveQuery(MenuDetail menuDetail1, int collectionSize){
        favList.add(menuDetail1);
        counter = collectionSize;
        if (favList.size()==counter)
            setRV();
    }

    public void setRV(){
        //set up recycler view
        mRecyclerView = findViewById(R.id.favRecyclerView);
        favAdapter = new FavAdapter(this, favList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(favAdapter);
    }

    public void btnOnClick_back(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }


}
