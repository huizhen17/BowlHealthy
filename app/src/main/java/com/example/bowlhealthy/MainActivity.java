package com.example.bowlhealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    String userID;

    TextView mtvUsername;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_home,new Fragment_Home()).commit();

        mtvUsername = findViewById(R.id.tvUsername); //username in menu header
        userID = mAuth.getCurrentUser().getUid();

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


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            DocumentReference documentReference = mFirestore.collection("userDetail").document(userID);
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

    public void btnOnClick_share(View view) {
        Intent shareMenu = new Intent(Intent.ACTION_SEND);
        shareMenu.setType("text/plain");
        String shareSub = "Bowl Healthiness ";
        String shareBody = "Hi Dear. \n\nSatisfy all your cravings with Bowl Healthiness today! Order now and self pick-up without waiting! So, what are you waiting for. Grab it now!\n\n\nBowl Healthiness\nGeorgetown Penang\nMalaysia\nTel no:012-3456789";
        shareMenu.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        shareMenu.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(shareMenu,"Share via"));
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
        Intent i;
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_home,new Fragment_Home()).commit();
                break;
            case R.id.nav_fav:
                i = new Intent(this,MyFavourite.class);
                startActivity(i);
                break;
            case R.id.nav_cart:
                i = new Intent(this,MyCart.class);
                startActivity(i);
                break;
            case R.id.nav_history:
                i = new Intent(this,OrderHistory.class);
                startActivity(i);
                break;
            case R.id.nav_account:
                i = new Intent(this,MyAccount .class);
                startActivity(i);
                break;
            case R.id.nav_faq:
                i = new Intent(this,FAQ .class);
                startActivity(i);
                break;
            case R.id.nav_tnc:
                i = new Intent(this,TermCondition .class);
                startActivity(i);
                break;
            case R.id.nav_about:
                i = new Intent(this,AboutUs .class);
                startActivity(i);
                break;
            case R.id.nav_logout:
                mAuth.signOut();
                i= new Intent(this,LoginActivity.class);
                startActivity(i);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
