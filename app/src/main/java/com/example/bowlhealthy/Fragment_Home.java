package com.example.bowlhealthy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class Fragment_Home extends Fragment {

    RecyclerView mRecyclerView;
    ViewPager viewPager;
    MainAdapter adapter;
    ArrayList<MainMenuItem> models;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        //Swipe view for home banner
        viewPager = v.findViewById(R.id.myViewPager);
        ViewBannerAdapter viewBannerAdapter = new ViewBannerAdapter(getContext());
        viewPager.setAdapter(viewBannerAdapter);

        //Swipe view for home menu category
        models = new ArrayList<>();
        models.add(new MainMenuItem(R.drawable.custombowl,"Custom Bowl",R.string.main_custom));
        models.add(new MainMenuItem(R.drawable.poke1,"Poke Bowl",R.string.main_poke));
        models.add(new MainMenuItem(R.drawable.buddha1,"Buddha Bowl",R.string.main_buddha));
        models.add(new MainMenuItem(R.drawable.burrito1,"Burrito Bowl",R.string.main_burrito));
        models.add(new MainMenuItem(R.drawable.smoothie1,"Smoothie Bowl",R.string.main_smoothie));

        mRecyclerView = v.findViewById(R.id.menuRecyclerView);

        //Design Horizontal Layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Set adapter for recycler view
        adapter = new MainAdapter(getContext(), models);
        mRecyclerView.setAdapter(adapter);

        return v;
    }
}
