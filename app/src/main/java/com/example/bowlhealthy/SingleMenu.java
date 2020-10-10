package com.example.bowlhealthy;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleMenu extends AppCompatActivity {

    ImageView mivImage;
    TextView mtvName,mtvDesc,mtvPrice;
    TextView mtvDuration, mtvCal;
    int image,desc;
    String name,textPrice, textDuration, textCal;
    float price, duration, cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_menu);

        mivImage = findViewById(R.id.ivItemBowl);
        mtvName = findViewById(R.id.tvItemName);
        mtvDesc = findViewById(R.id.tvItemDesc);
        mtvPrice = findViewById(R.id.tvItemPrice);
        mtvDuration = findViewById(R.id.tvItemDuration);
        mtvCal = findViewById(R.id.tvItemCal);

        Bundle bundle = getIntent().getExtras();
        image = bundle.getInt("image");
        name = bundle.getString("title");
        desc = bundle.getInt("desc");
        textPrice = bundle.getString("price");
        textDuration = bundle.getString("time");
        textCal = bundle.getString("calories");

        mivImage.setImageResource(image);
        mtvName.setText(name);
        mtvDuration.setText(textDuration);
        mtvCal.setText(textCal);
        mtvDesc.setText(desc);
        mtvPrice.setText(textPrice);

    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }

    public void btnOnClick_mycart(View view) {

    }

    public void btnOnClick_addCart(View view) {
        Toast.makeText(this,mtvName.getText().toString(),Toast.LENGTH_SHORT).show();
    }

    public void btnOnClick_shareItem(View view) {
    }
}
