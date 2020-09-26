package com.example.bowlhealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ViewPager viewPager;
    MainAdapter adapter;
    ArrayList<MainMenuItem> models;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Swipe view for home banner
        viewPager = findViewById(R.id.myViewPager);
        ViewBannerAdapter viewBannerAdapter = new ViewBannerAdapter(this);
        viewPager.setAdapter(viewBannerAdapter);

        //Swipe view for home menu category
        models = new ArrayList<>();
        models.add(new MainMenuItem(R.drawable.custombowl,"Custom Bowl",R.string.main_custom));
        models.add(new MainMenuItem(R.drawable.poke1,"Poke Bowl",R.string.main_poke));
        models.add(new MainMenuItem(R.drawable.buddha1,"Buddha Bowl",R.string.main_buddha));
        models.add(new MainMenuItem(R.drawable.burrito1,"Burrito Bowl",R.string.main_burrito));
        models.add(new MainMenuItem(R.drawable.smoothie1,"Smoothie Bowl",R.string.main_smoothie));

        mRecyclerView = findViewById(R.id.menuRecyclerView);

        //Design Horizontal Layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MainAdapter(MainActivity.this, models);
        //viewMainBowl.setAdapter(adapter);
        mRecyclerView.setAdapter(adapter);



    }
}
