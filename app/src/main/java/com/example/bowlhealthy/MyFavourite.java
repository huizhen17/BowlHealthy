package com.example.bowlhealthy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class MyFavourite extends AppCompatActivity implements FavAdapter.FavOnClick{

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView mRecyclerView;
    FavAdapter favAdapter;
    ArrayList<MenuDetail> favList = new ArrayList<>();
    String userID;
    int counter;
    TextView mtvEmptyTitle, mtvEmptyDesc;
    ImageView ivEmptyFav;
    Button btnBackMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);

        //Assign variable
        ivEmptyFav = findViewById(R.id.ivEmptyFav);
        mtvEmptyTitle = findViewById(R.id.tvEmptyFavTitle);
        mtvEmptyDesc = findViewById(R.id.tvEmptyFavDesc);
        btnBackMenu = findViewById(R.id.btnBackToMenu);
        btnBackMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyFavourite.this,MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        userID = mAuth.getCurrentUser().getUid();
        //Get instant update
        CollectionReference getMenuDB =  db.collection("userDetail").document(userID).collection("favDetail");
        getMenuDB.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error==null) {
                    if (value.isEmpty()){
                        ivEmptyFav.setVisibility(View.VISIBLE);
                        mtvEmptyTitle.setVisibility(View.VISIBLE);
                        mtvEmptyDesc.setVisibility(View.VISIBLE);
                        btnBackMenu.setVisibility(View.VISIBLE);
                    }else {
                        for (QueryDocumentSnapshot document : value) {
                            retrieveQuery(document.toObject(MenuDetail.class), value.size());
                        }
                    }

                }else
                    Toast.makeText(getApplicationContext(),"Fail to retrieve data.",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void retrieveQuery(MenuDetail menuDetail1, int collectionSize){
        favList.add(menuDetail1);
        counter = collectionSize;
        if (favList.size()==counter){
            setRV();
            ivEmptyFav.setVisibility(View.INVISIBLE);
            mtvEmptyTitle.setVisibility(View.INVISIBLE);
            mtvEmptyDesc.setVisibility(View.INVISIBLE);
            btnBackMenu.setVisibility(View.INVISIBLE);
        }
    }

    public void setRV(){
        //set up recycler view
        mRecyclerView = findViewById(R.id.favRecyclerView);
        favAdapter = new FavAdapter(this, favList,this);
        favAdapter.notifyDataSetChanged();
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

    @Override
    public void FavOnClick(int position) {
        //Place the content back to Single Menu page
        Intent intent = new Intent(MyFavourite.this,SingleMenu.class);
        intent.putExtra("image",favList.get(position).getMenuImg());
        intent.putExtra("title",favList.get(position).getMenuName());
        intent.putExtra("time",favList.get(position).getTime());
        intent.putExtra("calories",favList.get(position).getCalories());
        intent.putExtra("desc",favList.get(position).getMenuDesc());
        intent.putExtra("price",favList.get(position).getPrice());
        intent.putExtra("from","FAV");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
