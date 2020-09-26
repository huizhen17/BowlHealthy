package com.example.bowlhealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    EditText etUsername, etEmail, etPhone, etPassword, etRepass;
    String username, email, phone, password , repass;
    String userID;
    CheckBox cbCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsername = findViewById(R.id.sUpName);
        etEmail = findViewById(R.id.sUpEmail);
        etPhone = findViewById(R.id.sUpPhone);
        etPassword = findViewById(R.id.sUpPass);
        etRepass = findViewById(R.id.sUpRePass);
        cbCondition = findViewById(R.id.chkBox_tnc);
    }

    public void linkOnClick_signIn(View view) {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void btnOnClick_SignUp(View view) {
        username = etUsername.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        repass = etRepass.getText().toString().trim();

        if(username.isEmpty()){
            etUsername.setError("Name required");
            etUsername.requestFocus();
            return;
        }

        if(email.isEmpty()){
            etEmail.setError("Email required");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please enter valid email");
            etEmail.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            etPhone.setError("Phone required");
            etPhone.requestFocus();
            return;
        }

        if(password.isEmpty()){
            etPassword.setError("Password required");
            etPassword.requestFocus();
            return;
        }

        if(repass.isEmpty()){
            etRepass.setError("Please type again the password");
            etRepass.requestFocus();
            return;
        }

        if(!cbCondition.isChecked()){
            Toast.makeText(this,"You must agree with the terms and condition.",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Sign Up successfully.",
                                    Toast.LENGTH_SHORT).show();
                            userID = mAuth.getCurrentUser().getUid();

                            DocumentReference user = db.collection("userDetail").document(userID);
                            UserDetail userDetail = new UserDetail(username,email,phone);
                            //If system successfully store user details it will go to main if not it
                            //will toast message
                            user.set(userDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Intent intent = new Intent(SignUp.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    e.getMessage();
                                    Toast.makeText(SignUp.this, "Sign Up failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
