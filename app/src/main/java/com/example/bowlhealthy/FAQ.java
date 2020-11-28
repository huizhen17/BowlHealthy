package com.example.bowlhealthy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FAQ extends AppCompatActivity {

    RecyclerView recyclerView;
    List<QuestionAnswer> mqaList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_a_q);

        recyclerView = findViewById(R.id.recyclerView);

        mqaList = new ArrayList<>();

        mqaList.add(new QuestionAnswer("Q: Why should I buy online?","Speeding up the process. By ordering online you will you will get prices faster and you will be able to go through order confirmation and payment process much faster. "));
        mqaList.add(new QuestionAnswer("Q: What information should I input when ordering?","Our online ordering system will ask for all the important information you should submit."));
        mqaList.add(new QuestionAnswer("Q: Is there any delivery charges?","There is no any delivery charges as we are a pick up ordering system."));
        mqaList.add(new QuestionAnswer("Q: What are the terms and conditions?","You can see the terms and conditions here."));
        mqaList.add(new QuestionAnswer("Q: How do create an account?","You can create an account on the Sign Up page."));
        mqaList.add(new QuestionAnswer("Q: Can I reset my password?","Yes. You are allowed to reset password by enter the email address."));
        mqaList.add(new QuestionAnswer("Q: How do I change my personal details or email address?","No, we do not encourage user to change their personal details."));

        QAnsAdapter qAnsAdapter = new QAnsAdapter(mqaList);

        qAnsAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(qAnsAdapter);
        recyclerView.setHasFixedSize(true);
    }

    public void btnOnClick_back(View view) {
        super.onBackPressed();
    }
}
