package com.example.bowlhealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

public class OrderHistory extends AppCompatActivity {

    RecyclerView mMyHistoryRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        mMyHistoryRV = findViewById(R.id.myorderRV);

        //TODO::Adapter

    }

    public void btnHelp_onClick(View view) {
    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }
}
