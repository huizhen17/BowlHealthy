package com.example.bowlhealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        mMenuType = findViewById(R.id.tvMenuType);

        //Get passing menu type and display it
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("title");
        mMenuType.setText(type);

        menuList = new ArrayList<>();

        if(type.equalsIgnoreCase("Poke Bowl")){
            //Swipe view for home menu category
            menuList.add(new MenuDetail(R.drawable.poke1,"Signature Salmon Poke Bowl",R.string.poke1Ing,R.string.poke1Desc,"13.50", "20","650"));
            menuList.add(new MenuDetail(R.drawable.poke2,"Signature Tuna Poke Bowl",R.string.poke2Ing,R.string.poke2Desc, "13.50","20","640"));
            menuList.add(new MenuDetail(R.drawable.poke3,"Ahi Poke Bowl",R.string.poke3Ing,R.string.poke3Desc, "12.50","15","660"));
            menuList.add(new MenuDetail(R.drawable.poke4,"Avocado Salmon Poke Bowl",R.string.poke4Ing,R.string.poke4Desc, "12.50","15","639"));
            menuList.add(new MenuDetail(R.drawable.poke5,"Shrimp Poke Bowl",R.string.poke5Ing,R.string.poke5Desc, "12.50","15","639"));
        }
        else if(type.equalsIgnoreCase("Buddha Bowl")){
            menuList.add(new MenuDetail(R.drawable.buddha1,"Black Rice Salad Buddha Bowl",R.string.buddha1Ing,R.string.buddha1Desc, "12.50","15","439"));
            menuList.add(new MenuDetail(R.drawable.buddha2,"Mediterranean Buddha Bowl",R.string.buddha2Ing,R.string.buddha2Desc, "12.50","15","550"));
            menuList.add(new MenuDetail(R.drawable.buddha3,"Potato Chickpea Buddha Bowl",R.string.buddha3Ing,R.string.buddha3Desc, "12.50","15","437"));
            menuList.add(new MenuDetail(R.drawable.buddha4,"Tahini Miso Buddha Bowl",R.string.buddha4Ing,R.string.buddha4Desc, "11.50","15","487"));
            menuList.add(new MenuDetail(R.drawable.buddha5,"Nourishing Buddha Bowl",R.string.buddha5Ing,R.string.buddha5Desc, "11.50","15","539"));
        }
        else if (type.equalsIgnoreCase("Burrito Bowl")){
            menuList.add(new MenuDetail(R.drawable.burrito1,"Potato Black Bean Burrito Bowl",R.string.burrito1Ing,R.string.burrito1Desc, "12.50","15","440"));
            menuList.add(new MenuDetail(R.drawable.burrito2,"Chicken Shawarma Quinoa Bowl",R.string.burrito2Ing,R.string.burrito2Desc,  "12.50","15","580"));
            menuList.add(new MenuDetail(R.drawable.burrito3,"Roasted Chickpea Taco Bowl",R.string.burrito3Ing,R.string.burrito3Desc,  "12.00","15","555"));
            menuList.add(new MenuDetail(R.drawable.burrito4,"Mediterranean Quinoa Bowl",R.string.burrito4Ing,R.string.burrito4Desc,  "12.00","15","450"));
            menuList.add(new MenuDetail(R.drawable.burrito5,"Veggie Burrito Bowl",R.string.burrito5Ing,R.string.burrito5Desc,  "12.00","15","439"));
        }
        else if(type.equalsIgnoreCase("Smoothie Bowl")){
            menuList.add(new MenuDetail(R.drawable.smoothie1,"Cacao Smoothie",R.string.smoothie1Ing,R.string.smoothie1Desc,"10.50","15","328"));
            menuList.add(new MenuDetail(R.drawable.smoothie2,"Avocado Smoothie",R.string.smoothie2Ing,R.string.smoothie2Desc, "10.50","15","320"));
            menuList.add(new MenuDetail(R.drawable.smoothie3,"Mango Smoothie",R.string.smoothie3Ing,R.string.smoothie3Desc, "10.50","15","340"));
            menuList.add(new MenuDetail(R.drawable.smoothie4,"Berry Smoothie",R.string.smoothie4Ing,R.string.smoothie4Desc, "10.50","15","380"));
        }

        //set up recycler view
        mRecyclerView = findViewById(R.id.menuItemRecyclerView);
        menuAdapter = new MenuAdapter(this, menuList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(menuAdapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                menuAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }

    public void fabOnClick_cart(View view) {
    }
}
