package com.example.scarl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class otp_section extends AppCompatActivity {

    EditText otpphone, otpmail;
    Button otpsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_section);
        otpphone=findViewById(R.id.otpphone);
        otpmail=findViewById(R.id.otpmail);
        otpsubmit=findViewById(R.id.otpsubmit);
        otpsubmit.setOnClickListener(new View.OnClickListener() {
            Intent i7 = new Intent(getApplicationContext(), reset_password.class);
            @Override
            public void onClick(View v) {
                startActivity(i7);
            }
        });
    }
}
