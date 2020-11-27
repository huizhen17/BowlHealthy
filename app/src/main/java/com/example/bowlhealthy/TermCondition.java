package com.example.bowlhealthy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TermCondition extends AppCompatActivity {

    TextView mtvTNC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_condition);

        mtvTNC = findViewById(R.id.tvTNC);
        mtvTNC.setText(R.string.tnc);
    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }
}
