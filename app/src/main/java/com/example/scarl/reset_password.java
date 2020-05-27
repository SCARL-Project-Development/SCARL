package com.example.scarl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class reset_password extends AppCompatActivity {

    EditText newpass, renewpass;
    Button resetsubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        newpass=findViewById(R.id.newpass);
        renewpass=findViewById(R.id.renewpass);
        resetsubmit=findViewById(R.id.resetsubmit);
        resetsubmit.setOnClickListener(new View.OnClickListener() {
            Intent i8=new Intent(getApplicationContext(), HomeActivity.class);
            @Override
            public void onClick(View v) {
                startActivity(i8);
            }
        });
    }
}
