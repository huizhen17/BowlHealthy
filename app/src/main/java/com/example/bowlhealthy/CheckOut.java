package com.example.bowlhealthy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CheckOut extends AppCompatActivity {

    private static double TAX = 0.60;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<CartDetail> mcartDetail = new ArrayList<>();
    RecyclerView mRecyclerview;
    OrderItemAdapter orderItemAdapter;
    TextView mtvRecName, mtvRecPhone;
    TextView mtvOrderDate, mtvOrderTime;
    TextView mtvEstTime, mtvSubtotal, mtvTax, mtvTotalAmt;
    String id;
    Double total,subtotal;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    int hourOfDay,minOfDay;
    String mark="";
    String receiptID="";
    String name,phone,date,time,subTotal,amount,email;

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
        mtvTax = findViewById(R.id.tvCheckoutTax);
        mtvTotalAmt = findViewById(R.id.tvTotalAmt);

        //Returns the value associated with the given key from MyCart.java
        Bundle bundle = getIntent().getExtras();
        mtvSubtotal.setText(bundle.getString("subtotal"));
        subtotal = Double.valueOf(mtvSubtotal.getText().toString());
        total = subtotal + TAX;
        mtvTotalAmt.setText((String.format("%.2f", total)));

        if(bundle != null) {
            mcartDetail=getIntent().getParcelableArrayListExtra("cartList");
        }


        //Declare and assign date to local
        final Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int hour = cal.get(Calendar.HOUR);
        final int min = cal.get(Calendar.MINUTE);
        final int marker = cal.get(Calendar.AM_PM);
        String date = day + "/" + (month+1) + "/" + year;
        mtvOrderDate.setText(date);

        if(marker==0) {
            mark = "AM";
        }
        else {
            mark = "PM";
        }
        //Declare and assign time to local
        String time = hour + ":" + min + " " + mark;
        mtvOrderTime.setText(time);

        //When user wishes to change date
        mtvOrderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        //When user wishes to change date
        mtvOrderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CheckOut.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourDay, int minDay) {
                        hourOfDay = hourDay;
                        minOfDay = minDay;

                        String time = hourOfDay + ":" + minOfDay;
                        SimpleDateFormat f24hours = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24hours.parse(time);
                            SimpleDateFormat f12hours = new SimpleDateFormat("h:mm aa");
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
                email = documentSnapshot.getString("email");

                //Set user name and phone
                mtvRecName.setText(name);
                mtvRecPhone.setText(phone);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Problem on retrieving data. Please try again later.",Toast.LENGTH_SHORT).show();
            }
        });

        //Set up adapter
        mRecyclerview = findViewById(R.id.orderRecyclerView);
        orderItemAdapter = new OrderItemAdapter(this, mcartDetail);
        orderItemAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(layoutManager);
        mRecyclerview.setAdapter(orderItemAdapter);

    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }

    public void btnHelp_onClick(View view) {
        Intent i = new Intent(CheckOut.this,FAQ.class);
        startActivity(i);
    }

    public void btnOrder_onClick(View view) {
        //Generate random ID from firebase
        DocumentReference ref = db.collection("orderDetail").document();
        receiptID = ref.getId();

        name = mtvRecName.getText().toString();
        phone = mtvRecPhone.getText().toString();
        date = mtvOrderDate.getText().toString();
        time = mtvOrderTime.getText().toString();
        subTotal = mtvSubtotal.getText().toString();
        amount = mtvTotalAmt.getText().toString();

        ReceiptDetail repDetails = new ReceiptDetail(receiptID,name,phone,date,time,subTotal,amount);
        DocumentReference orderList = db.collection("userDetail").document(id).collection("orderDetail").document(receiptID);
        orderList.set(repDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CheckOut.this,"Fail to save. Try it later!",Toast.LENGTH_SHORT).show();
            }
        });

        CollectionReference orderCartQuery = db.collection("userDetail").document(id).collection("cartDetail");
        orderCartQuery.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots){
                    retrieveItem(queryDocumentSnapshot.toObject(CartDetail.class));
                }
            }
        });


        //Generated receipt will be sent to user
        String title = "View Your Receipt - Bowl Healthiness";
        String text ="Thank you for choosing us.\n Below is your invoice: \n\n"   +
                "Estimated time   : " +  mtvEstTime.getText().toString() + "mins" +
                "\nOrder Time     : " + time +
                "\nTotal Amount   : RM " + amount + "\n\n\nHave a nice day.";
        JavaMailAPI sendMail = new JavaMailAPI(this,email,title,text);
        sendMail.execute();

        Intent i = new Intent(CheckOut.this,SingleHistory.class);
        i.putExtra("receiptNo",receiptID);
        i.putExtra("name",name);
        i.putExtra("phone",phone);
        i.putExtra("date",date);
        i.putExtra("time",time);
        i.putExtra("subtotal",subTotal);
        i.putExtra("amount",amount);
        i.putParcelableArrayListExtra("cartList",mcartDetail);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    public void retrieveItem(CartDetail cartDetailQuery){
        String cartItemMenu = cartDetailQuery.getCartMenu();
        String cartItemQty = cartDetailQuery.getCartQty();
        CartDetail mcartDetail = new CartDetail(0,cartItemMenu,"",cartItemQty);

        //Get retrieve item and store into firebase
        DocumentReference cartItemList = db.collection("userDetail").document(id).collection("orderDetail").document(receiptID).collection("cartItemDetail").document(cartItemMenu);
        cartItemList.set(mcartDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CheckOut.this,"Fail to save. Try it later!",Toast.LENGTH_SHORT).show();
            }
        });

        //Delete item from Cart List
        DocumentReference getMenuDB =  db.collection("userDetail").document(id).collection("cartDetail").document(cartItemMenu);
        getMenuDB.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CheckOut.this,"Error. Try it later!",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
