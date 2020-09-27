package com.example.bowlhealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    EditText metEmailRecover;
    String emailRecover;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        metEmailRecover = findViewById(R.id.etSendEmail);
    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }

    public void btnOnClick_send(View view) {
        emailRecover = metEmailRecover.getText().toString();

        if(emailRecover.isEmpty()){
            metEmailRecover.setError("Email required");
            metEmailRecover.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailRecover).matches()){
            metEmailRecover.setError("Please enter valid email");
            metEmailRecover.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(emailRecover).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this,"Password send to your email",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgetPassword.this,LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ForgetPassword.this,"Some error occured.Please try again later!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
