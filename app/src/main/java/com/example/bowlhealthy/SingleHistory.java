package com.example.bowlhealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class SingleHistory extends AppCompatActivity {
    TextView mtvReceiptNo,mtvHisName, mtvHisPhone;
    TextView mtvHisDate, mtvHisTime;
    TextView mtvEstTime, mtvHisSubtotal, mtvTax, mtvHisTotalAmt;
    RecyclerView mhisRecyclerView;
    OrderItemAdapter orderItemAdapter;
    ArrayList<CartDetail> mcartDetail = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_history);

        mtvReceiptNo = findViewById(R.id.tvReceiptNo);
        mtvHisName = findViewById(R.id.tvHistoryName);
        mtvHisPhone = findViewById(R.id.tvHistoryPhone);
        mtvHisDate = findViewById(R.id.tvHistoryDate);
        mtvHisTime = findViewById(R.id.tvHistoryTime);
        mtvEstTime = findViewById(R.id.tvEstimatedTime);
        mtvHisSubtotal = findViewById(R.id.tvHistorySubtotal);
        mtvTax = findViewById(R.id.tvHistoryTax);
        mtvHisTotalAmt = findViewById(R.id.tvHistoryAmt);

        Bundle bundle = getIntent().getExtras();
        mtvReceiptNo.setText(bundle.get("receiptNo").toString());
        mtvHisName.setText(bundle.get("name").toString());
        mtvHisPhone.setText(bundle.get("phone").toString());
        mtvHisDate.setText(bundle.get("date").toString());
        mtvHisTime.setText(bundle.get("time").toString());
        mtvHisSubtotal.setText(bundle.get("subtotal").toString());
        mtvHisTotalAmt.setText(bundle.get("amount").toString());

        if(bundle != null) {
            mcartDetail=getIntent().getParcelableArrayListExtra("cartList");
        }



        //Set up adapter
        mhisRecyclerView = findViewById(R.id.singleHistoryRecyclerView);
        orderItemAdapter = new OrderItemAdapter(this, mcartDetail);
        orderItemAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mhisRecyclerView.setLayoutManager(layoutManager);
        mhisRecyclerView.setAdapter(orderItemAdapter);

    }

    public void btnOnClick_back(View view) {
        Intent i = new Intent(SingleHistory.this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void btnShare_onClick(View view) {
    }
}
