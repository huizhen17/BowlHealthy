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
    ArrayList<MenuDetail> pokeList, buddhaList, burritoList, smoothieList ;

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

        //Swipe view for home menu category
        pokeList = new ArrayList<>();
        pokeList.add(new MenuDetail(R.drawable.poke1,R.string.poke1Name,R.string.poke1Ing, "13.50","20"));
        pokeList.add(new MenuDetail(R.drawable.poke2,R.string.poke2Name,R.string.poke2Ing, "13.50","20"));
        pokeList.add(new MenuDetail(R.drawable.poke3,R.string.poke3Name,R.string.poke3Ing, "12.50","15"));
        pokeList.add(new MenuDetail(R.drawable.poke4,R.string.poke4Name,R.string.poke4Ing, "12.50","15"));
        pokeList.add(new MenuDetail(R.drawable.poke5,R.string.poke5Name,R.string.poke5Ing, "12.50","15"));

        //set up recycler view
        mRecyclerView = findViewById(R.id.menuItemRecyclerView);
        menuAdapter = new MenuAdapter(this, pokeList);
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
