package com.example.bowlhealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.Inet4Address;

public class MyAccount extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView mtvName,mtvEmail,mtvPhone,mtvPass;
    String oldpassword,newpassword,repassword;
    EditText etOldPass, etNewPass, etRePass;
    Button btnEdit, btnCancel, btnChange;
    String userID;
    Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        mtvName = findViewById(R.id.tvUsername);
        mtvEmail = findViewById(R.id.tvEmail);
        mtvPhone = findViewById(R.id.tvPassword);
        mtvPass = findViewById(R.id.tvPassword);
        btnEdit = findViewById(R.id.btnEdit);

        //Means i wan run in this activity, the this will pass the context to the system (like address or sth la
        loadingDialog = new Dialog(this);

        userID = mAuth.getCurrentUser().getUid();
        DocumentReference user = db.collection("userDetail").document(userID);
        user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name = documentSnapshot.getString("name");
                String email = documentSnapshot.getString("email");
                String phone = documentSnapshot.getString("phone");

                mtvName.setText(name);
                mtvEmail.setText(email);
                mtvPhone.setText(phone);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Problem on retrieving data. Please try again later.",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void btnOnClick_editPs(View view) {
        //Initialize loading dialog
        loadingDialog.setContentView(R.layout.custom_dialog);
        loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loadingDialog.setCancelable(false);

        ProgressBar progressBar = loadingDialog.findViewById(R.id.spin_kit);
        Sprite circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);

        //Create BottomSheetDialog
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MyAccount.this,R.style.BottomSheetDialog);
        bottomSheetDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bottomSheetDialog.setContentView(R.layout.bs_editpass_dialog);
        bottomSheetDialog.show();

        //Initialize and assign variable
        etOldPass = bottomSheetDialog.findViewById(R.id.oldPass);
        etNewPass = bottomSheetDialog.findViewById(R.id.newPass);
        etRePass = bottomSheetDialog.findViewById(R.id.rePass);
        btnCancel = bottomSheetDialog.findViewById(R.id.btnCancel);
        btnChange = bottomSheetDialog.findViewById(R.id.btnChange);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldpassword = etOldPass.getText().toString();
                newpassword = etNewPass.getText().toString();
                repassword = etRePass.getText().toString();

                if(oldpassword.isEmpty()){
                    etOldPass.setError("Password required");
                    etOldPass.requestFocus();
                    return;
                }

                if(newpassword.isEmpty()){
                    etNewPass.setError("Password required");
                    etNewPass.requestFocus();
                    return;
                }

                if(repassword.isEmpty()){
                    etRePass.setError("Password required");
                    etRePass.requestFocus();
                    return;
                }

                if(!newpassword.equals(repassword)){
                    Toast.makeText(view.getContext(),"Both password are not match!",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(newpassword.equals(oldpassword)){
                    Toast.makeText(view.getContext(),"Old password is same as new password!",Toast.LENGTH_SHORT).show();
                    return;
                }

                final FirebaseUser user = mAuth.getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),oldpassword);

                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            user.updatePassword(newpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        loadingDialog.show();
                                        Toast.makeText(getApplicationContext(),"Password changed successfully.",Toast.LENGTH_SHORT).show();
                                        mtvPass.setText(newpassword);
                                        JavaMailAPI javaMailAPI = new JavaMailAPI(getApplicationContext(),mtvEmail.getText().toString(),"Password Changed for Bowl Healthiness Account",getResources().getString(R.string.myacc_passwordchange));
                                        javaMailAPI.execute();

                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                loadingDialog.dismiss();
                                                bottomSheetDialog.dismiss();
                                            }
                                        },2000); // delay 2 sec

                                    }
                                    else
                                        Toast.makeText(getApplicationContext(),"Password fail to change.",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(),"Password fail to change.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

    }

    public void btnOnClick_back(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
