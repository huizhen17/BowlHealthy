package com.example.bowlhealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    String userID;
    RecyclerView mRecyclerView;
    ViewPager viewPager;
    MainAdapter adapter;
    ArrayList<MainMenuItem> models;
    TextView mtvUsername;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mtvUsername = findViewById(R.id.tvUsername); //username in menu header
        userID = mAuth.getCurrentUser().getUid();
        Toast.makeText(this,"Hello!!!!!!!"+ userID,Toast.LENGTH_SHORT).show();

        //Set up drawer navigation
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        //Set Menu Icon On Click
        findViewById(R.id.ivMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        navigationView.setCheckedItem(R.id.nav_home);

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

        //Set adapter for recycler view
        adapter = new MainAdapter(MainActivity.this, models);
        mRecyclerView.setAdapter(adapter);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            DocumentReference documentReference = mFirestore.collection("userDetail").document(userID);

            /*documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                    mtvGreetingName.setText(documentSnapshot.getString("name"));
                    View headerView = navigationView.getHeaderView(0);
                    TextView navUsername = (TextView) headerView.findViewById(R.id.tv_username);
                    navUsername.setText(documentSnapshot.getString("name"));
                }
            });
             */

            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    View headerView = navigationView.getHeaderView(0);
                    TextView navUsername = (TextView) headerView.findViewById(R.id.tvUsername);
                    navUsername.setText(documentSnapshot.getString("name"));
                }
            });

        }

    }

    public void btnOnClick_search(View view) {

    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_fav:
                break;
            case R.id.nav_cart:
                break;
            case R.id.nav_history:
                break;
            case R.id.nav_account:
                break;
            case R.id.nav_faq:
                break;
            case R.id.nav_tnc:
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
