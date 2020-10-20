package com.example.bowlhealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CheckOut extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView mtvRecName, mtvRecPhone;
    TextView mtvOrderDate, mtvOrderTime;
    TextView mtvEstTime, mtvSubtotal, mtvTotalAmt;
    String id;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    TimePickerDialog.OnTimeSetListener mTimeSetListener;
    int hour,min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        mtvRecName = findViewById(R.id.tvRecName);
        mtvRecPhone = findViewById(R.id.tvRecPhone);
        mtvOrderDate = findViewById(R.id.tvOrderDate);
        mtvOrderTime = findViewById(R.id.tvOrderTime);
        mtvEstTime = findViewById(R.id.tvEstimatedTime);
        mtvSubtotal = findViewById(R.id.tvCheckoutSubtotal);
        mtvTotalAmt = findViewById(R.id.tvTotalAmt);

        Bundle bundle = getIntent().getExtras();
        mtvSubtotal.setText(bundle.getString("subtotal"));
        mtvTotalAmt.setText(bundle.getString("subtotal"));

        //TODO::Date + Time
        mtvOrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CheckOut.this,
                        R.style.DatePickerTheme,mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String date = day + "/" + (month+1) + "/" + year;
                mtvOrderDate.setText(date);
            }
        };

        //Set time
        mtvOrderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(CheckOut.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourDay, int minDay) {
                        hour = hourDay;
                        min = minDay;

                        String time = hour + ":" + min;
                        SimpleDateFormat f24hours = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24hours.parse(time);
                            SimpleDateFormat f12hours = new SimpleDateFormat("HH:mm aa");
                            mtvOrderTime.setText(f12hours.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                },hour,min,false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                timePickerDialog.show();
            }
        });


        //Retrieve user info from database
        id = mAuth.getCurrentUser().getUid();
        DocumentReference user = db.collection("userDetail").document(id);
        user.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String name = documentSnapshot.getString("name");
                String phone = documentSnapshot.getString("phone");

                mtvRecName.setText(name);
                mtvRecPhone.setText(phone);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Problem on retrieving data. Please try again later.",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }

    public void btnHelp_onClick(View view) {
    }

    public void btnOrder_onClick(View view) {
    }
}
