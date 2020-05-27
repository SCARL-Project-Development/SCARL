package com.example.scarl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class forgot_password extends AppCompatActivity {

    EditText devno,selfph;
    Button forgotsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        devno=findViewById(R.id.devno);
        selfph=findViewById(R.id.selfph);
        forgotsubmit=findViewById(R.id.forgotsubmit);
        forgotsubmit.setOnClickListener(new View.OnClickListener() {
            Intent i6=new Intent(getApplicationContext(), otp_section.class);
            @Override
            public void onClick(View v) {
                startActivity(i6);
            }
        });
    }
}
