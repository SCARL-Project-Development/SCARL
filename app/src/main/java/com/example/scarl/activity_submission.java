package com.example.scarl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.valueOf;

public class activity_submission extends AppCompatActivity {
    EditText logindev,loginpass;
    CheckBox remember;
    Button login;
    TextView forgotpass;
    SharedPreferences sharedPreferences, shrdprf;
    SharedPreferences.Editor editor, edtr;
    String myPrefs = "MyPrefs";
    String myPrf = "MyPrf";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission);
        logindev=findViewById(R.id.logindev);
        loginpass=findViewById(R.id.loginpass);
        remember=findViewById(R.id.remember);
        login=findViewById(R.id.login);
        forgotpass=findViewById(R.id.forgotpass);
        sharedPreferences = getSharedPreferences(myPrefs, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        shrdprf = getSharedPreferences(myPrf, MODE_PRIVATE);
        edtr = shrdprf.edit();
        login.setOnClickListener(new View.OnClickListener() {
            Intent i4 = new Intent(getApplicationContext(), base.class);
            @Override
            public void onClick(View v) {
                editor.putString("DeviceNo.", logindev.getText().toString());
                editor.commit();

                final DocumentReference docRef = db.collection("authorize").document(logindev.getText().toString());
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                Map<String, Object> authorize = document.getData();

                                assert authorize != null;
                                if (loginpass.getText().toString().equals(authorize.get("pass_reg").toString())){
                                    Toast.makeText(activity_submission.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                    if(remember.isChecked()){
                                        edtr.putInt("Check", 1);

                                    }
                                    else
                                        edtr.putInt("Check", 0);
                                    edtr.putInt("DevNo", Integer.parseInt(logindev.getText().toString()));
                                    edtr.commit();
                                    i4.putExtra("logindev",logindev.getText().toString());
                                    startActivity(i4);
                                }
                                else{
                                    Toast.makeText(activity_submission.this,"Wrong Password! Try again",Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Log.d("TAG", "No such document");
                                Toast.makeText(activity_submission.this,"Not registered!",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                            Toast.makeText(activity_submission.this,"Something went wrong!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            Intent i5 = new Intent(getApplicationContext(), forgot_password.class);
            @Override
            public void onClick(View v) {
                startActivity(i5);
            }
        });
    }
}
