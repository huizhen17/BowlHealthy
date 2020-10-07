package com.example.bowlhealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    TextView mMenuType;
    String type;
    RecyclerView mRecyclerView;
    MenuAdapter menuAdapter;
    ArrayList<MenuDetail> menuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Set scroll view focus up
        /*((ScrollView) findViewById(R.id.scrollView)).post(new Runnable() {
            public void run() {
                ((ScrollView) findViewById(R.id.scrollView)).fullScroll(View.FOCUS_UP);
            }
        });*/

        mMenuType = findViewById(R.id.tvMenuType);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("title");//base on the title get thn if(title.equal("Poke Bowl")) show list
        mMenuType.setText(type);

        menuList = new ArrayList<>();

        if(type.equalsIgnoreCase("Poke Bowl")){
            //Swipe view for home menu category
            menuList.add(new MenuDetail(R.drawable.poke1,R.string.poke1Name,R.string.poke1Ing, "13.50","20"));
            menuList.add(new MenuDetail(R.drawable.poke2,R.string.poke2Name,R.string.poke2Ing, "13.50","20"));
            menuList.add(new MenuDetail(R.drawable.poke3,R.string.poke3Name,R.string.poke3Ing, "12.50","15"));
            menuList.add(new MenuDetail(R.drawable.poke4,R.string.poke4Name,R.string.poke4Ing, "12.50","15"));
            menuList.add(new MenuDetail(R.drawable.poke5,R.string.poke5Name,R.string.poke5Ing, "12.50","15"));
        }
        else if(type.equalsIgnoreCase("Buddha Bowl")){
            menuList.add(new MenuDetail(R.drawable.buddha1,R.string.buddha1Name,R.string.buddha1Ing, "12.50","15"));
            menuList.add(new MenuDetail(R.drawable.buddha2,R.string.buddha2Name,R.string.buddha2Ing, "12.50","15"));
            menuList.add(new MenuDetail(R.drawable.buddha3,R.string.buddha3Name,R.string.buddha3Ing, "12.50","15"));
            menuList.add(new MenuDetail(R.drawable.buddha4,R.string.buddha4Name,R.string.buddha4Ing, "11.50","15"));
            menuList.add(new MenuDetail(R.drawable.buddha5,R.string.buddha5Name,R.string.buddha5Ing, "11.50","15"));
        }
        else if (type.equalsIgnoreCase("Burrito Bowl")){
            menuList.add(new MenuDetail(R.drawable.burrito1,R.string.burrito1Name,R.string.burrito1Ing, "12.50","15"));
            menuList.add(new MenuDetail(R.drawable.burrito2,R.string.burrito2Name,R.string.burrito2Ing, "12.50","15"));
            menuList.add(new MenuDetail(R.drawable.burrito3,R.string.burrito3Name,R.string.burrito3Ing, "12.00","15"));
            menuList.add(new MenuDetail(R.drawable.burrito4,R.string.burrito4Name,R.string.burrito4Ing, "12.00","15"));
            menuList.add(new MenuDetail(R.drawable.burrito5,R.string.burrito5Name,R.string.burrito5Ing, "12.00","15"));
        }
        else if(type.equalsIgnoreCase("Smoothie Bowl")){
            menuList.add(new MenuDetail(R.drawable.smoothie1,R.string.smoothie1Name,R.string.smoothie1Ing, "10.50","15"));
            menuList.add(new MenuDetail(R.drawable.smoothie2,R.string.smoothie2Name,R.string.smoothie2Ing, "10.50","15"));
            menuList.add(new MenuDetail(R.drawable.smoothie3,R.string.smoothie3Name,R.string.smoothie3Ing, "10.50","15"));
            menuList.add(new MenuDetail(R.drawable.smoothie4,R.string.smoothie4Name,R.string.smoothie4Ing, "10.50","15"));
        }

        //set up recycler view
        mRecyclerView = findViewById(R.id.menuItemRecyclerView);
        menuAdapter = new MenuAdapter(this, menuList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(menuAdapter);

    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }

    public void btnOnClick_search(View view) {
    }
}
