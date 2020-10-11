package com.example.bowlhealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MyFavourite extends AppCompatActivity {

    RecyclerView mRecyclerView;
    FavAdapter favAdapter;
    ArrayList<MenuDetail> favList;
    String name,duration,calories;
    int image,desc;
    String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);

        //Get variable from Single Menu Activity
        Bundle bundle = getIntent().getExtras();
        image = bundle.getInt("image");
        name = bundle.getString("name");
        price = bundle.getString("price");
        desc = bundle.getInt("desc");
        calories = bundle.getString("calories");
        duration = bundle.getString("duration");

        //declare new ArrayList for storing favourite list
        favList = new ArrayList<>();
        favList.add(new MenuDetail(image,name,desc,price,duration,calories));

        //set up recycler view
        mRecyclerView = findViewById(R.id.favRecyclerView);
        favAdapter = new FavAdapter(this, favList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(favAdapter);

    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }
}
