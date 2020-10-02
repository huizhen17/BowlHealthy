package com.example.bowlhealthy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MyAccount extends AppCompatActivity {


    TextView mtvName,mtvEmail,mtvPhone,mtvPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        mtvName = findViewById(R.id.tvUsername);
        mtvEmail = findViewById(R.id.tvEmail);
        mtvPhone = findViewById(R.id.tvPassword);
        mtvPass = findViewById(R.id.tvPassword);
    }

    public void btnOnClick_editPs(View view) {
        Toast.makeText(this,"Hello!",Toast.LENGTH_SHORT).show();
    }

    public void btnOnClick_back(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
