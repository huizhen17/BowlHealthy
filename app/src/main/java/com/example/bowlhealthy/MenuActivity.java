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
            menuList.add(new MenuDetail(R.drawable.poke1,R.string.poke1Name,R.string.poke1Ing,R.string.poke1Desc,"13.50", "20","650"));
            menuList.add(new MenuDetail(R.drawable.poke2,R.string.poke2Name,R.string.poke2Ing,R.string.poke2Desc, "13.50","20","640"));
            menuList.add(new MenuDetail(R.drawable.poke3,R.string.poke3Name,R.string.poke3Ing,R.string.poke3Desc, "12.50","15","660"));
            menuList.add(new MenuDetail(R.drawable.poke4,R.string.poke4Name,R.string.poke4Ing,R.string.poke4Desc, "12.50","15","639"));
            menuList.add(new MenuDetail(R.drawable.poke5,R.string.poke5Name,R.string.poke5Ing,R.string.poke5Desc, "12.50","15","639"));
        }
        else if(type.equalsIgnoreCase("Buddha Bowl")){
            menuList.add(new MenuDetail(R.drawable.buddha1,R.string.buddha1Name,R.string.buddha1Ing,R.string.buddha1Desc, "12.50","15","439"));
            menuList.add(new MenuDetail(R.drawable.buddha2,R.string.buddha2Name,R.string.buddha2Ing,R.string.buddha2Desc, "12.50","15","550"));
            menuList.add(new MenuDetail(R.drawable.buddha3,R.string.buddha3Name,R.string.buddha3Ing,R.string.buddha3Desc, "12.50","15","437"));
            menuList.add(new MenuDetail(R.drawable.buddha4,R.string.buddha4Name,R.string.buddha4Ing,R.string.buddha4Desc, "11.50","15","487"));
            menuList.add(new MenuDetail(R.drawable.buddha5,R.string.buddha5Name,R.string.buddha5Ing,R.string.buddha5Desc, "11.50","15","539"));
        }
        else if (type.equalsIgnoreCase("Burrito Bowl")){
            menuList.add(new MenuDetail(R.drawable.burrito1,R.string.burrito1Name,R.string.burrito1Ing,R.string.burrito1Desc, "12.50","15","440"));
            menuList.add(new MenuDetail(R.drawable.burrito2,R.string.burrito2Name,R.string.burrito2Ing,R.string.burrito2Desc,  "12.50","15","580"));
            menuList.add(new MenuDetail(R.drawable.burrito3,R.string.burrito3Name,R.string.burrito3Ing,R.string.burrito3Desc,  "12.00","15","555"));
            menuList.add(new MenuDetail(R.drawable.burrito4,R.string.burrito4Name,R.string.burrito4Ing,R.string.burrito4Desc,  "12.00","15","450"));
            menuList.add(new MenuDetail(R.drawable.burrito5,R.string.burrito5Name,R.string.burrito5Ing,R.string.burrito5Desc,  "12.00","15","439"));
        }
        else if(type.equalsIgnoreCase("Smoothie Bowl")){
            menuList.add(new MenuDetail(R.drawable.smoothie1,R.string.smoothie1Name,R.string.smoothie1Ing,R.string.smoothie1Desc,"10.50","15","328"));
            menuList.add(new MenuDetail(R.drawable.smoothie2,R.string.smoothie2Name,R.string.smoothie2Ing,R.string.smoothie2Desc, "10.50","15","320"));
            menuList.add(new MenuDetail(R.drawable.smoothie3,R.string.smoothie3Name,R.string.smoothie3Ing,R.string.smoothie3Desc, "10.50","15","340"));
            menuList.add(new MenuDetail(R.drawable.smoothie4,R.string.smoothie4Name,R.string.smoothie4Ing,R.string.smoothie4Desc, "10.50","15","380"));
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
