package com.example.bowlhealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SingleMenu extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ImageView mivImage,ivIcon;
    TextView mtvName,mtvDesc,mtvPrice;
    TextView mtvDuration, mtvCal;
    int image,desc;
    String name,textPrice, textDuration, textCal,menuID,userID;
    float price, duration, cal;
    Boolean saved = false;
    String from;

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
        ivIcon = findViewById(R.id.ivFav);

        Bundle bundle = getIntent().getExtras();
        image = bundle.getInt("image");
        name = bundle.getString("title");
        desc = bundle.getInt("desc");
        textPrice = bundle.getString("price");
        textDuration = bundle.getString("time");
        textCal = bundle.getString("calories");

        if (bundle.getString("from")!=null){
            from = bundle.getString("from");
        }

        mivImage.setImageResource(image);
        mtvName.setText(name);
        mtvDuration.setText(textDuration);
        mtvCal.setText(textCal);
        mtvDesc.setText(desc);
        mtvPrice.setText(textPrice);

        menuID = name.trim();
        userID = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("userDetail").document(userID).collection("favDetail").document(menuID);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    updateExist();
                }
            }
        });
    }

    public void updateExist(){
        saved = true;
        ivIcon.setImageResource(R.drawable.fav_icon);
    }

    public void btnOnClick_back(View view) {
        if(from!=null){
            Intent i = new Intent(this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else
            super.onBackPressed();
    }

    public void btnOnClick_mycart(View view) {

    }

    public void btnOnClick_addCart(View view) {
        Toast.makeText(this,mtvName.getText().toString(),Toast.LENGTH_SHORT).show();
    }

    public void btnOnClick_shareItem(View view) {
    }

    public void ivOnClick_Favourite(View view) {

        //TODO::PROBLEM

        if(saved){
            ivIcon.setImageResource(R.drawable.heart);
            saved = false;
            DocumentReference getMenuDB =  db.collection("userDetail").document(userID).collection("favDetail").document(menuID);
            getMenuDB.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(SingleMenu.this,"This menu is removed from favourite list.",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SingleMenu.this,"Error. Try it later!",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            ivIcon.setImageResource(R.drawable.fav_icon);
            MenuDetail favDetails = new MenuDetail(image,name,desc,textPrice,textDuration,textCal);
            DocumentReference favList = db.collection("userDetail").document(userID).collection("favDetail").document(menuID);
            favList.set(favDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(SingleMenu.this,"This menu is saved.",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SingleMenu.this,"Fail to save. Try it later!",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
