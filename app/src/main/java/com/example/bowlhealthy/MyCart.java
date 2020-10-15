package com.example.bowlhealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCart extends AppCompatActivity {

    RecyclerView mRecyclerview;
    ImageView mivEmptyCart, mivCartBg;
    TextView mtvEmptyTitle, mtvEmptyDesc, mtvCartRm, mtvSubtotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        mivEmptyCart = findViewById(R.id.ivEmptyCart);
        mivCartBg = findViewById(R.id.ivCartBg);
        mtvEmptyTitle = findViewById(R.id.tvEmptyCartTitle);
        mtvEmptyDesc = findViewById(R.id.tvEmptyCartDesc);
        mtvCartRm = findViewById(R.id.textRM);
        mtvSubtotal = findViewById(R.id.tvSubtotal);

        mivCartBg.setVisibility(View.INVISIBLE);
        mtvCartRm.setVisibility(View.INVISIBLE);
        mtvSubtotal.setVisibility(View.INVISIBLE);
    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }

    public void btnOnClick_checkOut(View view) {
    }
}
