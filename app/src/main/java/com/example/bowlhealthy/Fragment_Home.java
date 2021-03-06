package com.example.bowlhealthy;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
    TextView mtvFactTitle, mtvFactDesc;
    ArrayList<ArrayList<String>> factArray = new ArrayList<>();
    String factData[][]={
            {"Why Grain Bowl Food Trend?","Bowls-As-Meals has become part of culinary vocabulary."},
            {"Are bowls for weight loss?","Keep bowl as a salad with the same weight loss to boot."},
            {"Why are bowls so popular?","Food stylists said bowls make health foods photogenic."},
            {"Who invented bowls?","British anthropologist, Sir Flinders Petrie discovered it."},
            {"Are Buddha Bowls cold?","A Buddha bowl is a vegetarian meal which served cold. "},
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        //Swipe view for home banner
        viewPager = v.findViewById(R.id.myViewPager);
        ViewBannerAdapter viewBannerAdapter = new ViewBannerAdapter(getContext());
        viewPager.setAdapter(viewBannerAdapter);

        Timer timer = new Timer();
        timer.schedule(new MyTimeTask(),2000, 4000);

        //Swipe view for home menu category
        models = new ArrayList<>();
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

        v.findViewById(R.id.fab_ViewCart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MyCart.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mtvFactTitle = v.findViewById(R.id.tvFactTitle);
        mtvFactDesc = v.findViewById(R.id.tvFactDesc);

        for(int i=0;i<factData.length;i++){
            ArrayList<String> tempArray = new ArrayList<>();
            tempArray.add(factData[i][0]); //Fact title
            tempArray.add(factData[i][1]); //Fact desc

            factArray.add(tempArray);
        }

        showRandomFact();

        return v;
    }

    private void showRandomFact() {
        Random random = new Random();
        int randNo = random.nextInt(factArray.size());

        ArrayList<String> facts = factArray.get(randNo);
        mtvFactTitle.setText(facts.get(0));
        mtvFactDesc.setText(facts.get(1));

        facts.remove(0);
        Collections.shuffle(facts);
        factArray.remove(randNo);
    }

    public class MyTimeTask extends TimerTask{

        @Override
        public void run() {
            if(getActivity()!=null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(viewPager.getCurrentItem()==0){
                            viewPager.setCurrentItem(1);
                        }
                        else if(viewPager.getCurrentItem()==1){
                            viewPager.setCurrentItem(2);
                        }
                        else{
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }

        }
    }
}
