package com.example.scarl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    Button register, login;
    TextView tnc;
    SharedPreferences shrdprf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        tnc = findViewById(R.id.tnc);
        shrdprf=getSharedPreferences("MyPrf", MODE_PRIVATE);
        int i=shrdprf.getInt("Check",0);
        String logindev=Integer.toString(shrdprf.getInt("DevNo",0));
        if(i==1){
            Intent intent=new Intent(getApplicationContext(), base.class);
            startActivity(intent);
            intent.putExtra("logindev",logindev);
            finish();
        }
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(getApplicationContext(), register.class);
                startActivity(i1);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(getApplicationContext(), activity_submission.class);
                startActivity(i2);
            }
        });
    }
}
