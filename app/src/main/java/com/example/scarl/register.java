package com.example.scarl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    EditText name, address, self, mail, vehicle, puc, engine, dln, imei, device, pass_reg, pass_re, bloodg, alternate;
    CheckBox checktnc;
    Button regbutton;
    SharedPreferences sharedPreferences;
    String myPrefs = "MyPrefs";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.name);
        address=findViewById(R.id.address);
        self=findViewById(R.id.self);
        alternate=findViewById(R.id.alternate);
        mail=findViewById(R.id.mail);
        vehicle=findViewById(R.id.vehicle);
        puc=findViewById(R.id.puc);
        engine=findViewById(R.id.engine);
        dln=findViewById(R.id.dln);
        imei=findViewById(R.id.imei);
        device=findViewById(R.id.device);
        pass_reg=findViewById(R.id.pass_reg);
        pass_re=findViewById(R.id.pass_re);
        bloodg=findViewById(R.id.bloodg);
        checktnc=findViewById(R.id.checktnc);
        regbutton=findViewById(R.id.regbutton);
        sharedPreferences = getSharedPreferences(myPrefs, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(getApplicationContext(), HomeActivity.class);
                if(name.getText().toString() != "" && address.getText().toString() != "" && self.getText().toString() != "" && alternate.getText().toString() != "" && mail.getText().toString() != "" && vehicle.getText().toString() != "" && puc.getText().toString()
                        != "" && engine.getText().toString() != "" && dln.getText().toString() != "" && imei.getText().toString() != "" && device.getText().toString() != "" && pass_reg.getText().toString()!= "" &&
                        pass_re.getText().toString() != "" && bloodg.getText().toString() != "" && checktnc.isChecked()){
                    /*editor.putString("name", name.getText().toString());
                    editor.putString("address", address.getText().toString());
                    editor.putString("self", self.getText().toString());
                    editor.putString("alternate", alternate.getText().toString());
                    editor.putString("mail", mail.getText().toString());
                    editor.putString("vehicle", vehicle.getText().toString());
                    editor.putString("puc", puc.getText().toString());
                    editor.putString("engine", engine.getText().toString());
                    editor.putString("dln", dln.getText().toString());
                    editor.putString("imei", imei.getText().toString());
                    editor.putString("device", device.getText().toString());
                    editor.putString("pass_reg", pass_reg.getText().toString());
                    editor.putString("bloodg", bloodg.getText().toString());
                    editor.commit();*/


                    Map<String, Object> user = new HashMap<>();
                    user.put("address",address.getText().toString());
                    user.put("alternate",alternate.getText().toString());
                    user.put("bloodg",bloodg.getText().toString());
                    user.put("device",device.getText().toString());
                    user.put("dln",dln.getText().toString());
                    user.put("engine",engine.getText().toString());
                    user.put("imei",imei.getText().toString());
                    user.put("mail",mail.getText().toString());
                    user.put("name",name.getText().toString());
                    user.put("puc",puc.getText().toString());
                    user.put("self",self.getText().toString());
                    user.put("vehicle",vehicle.getText().toString());
                    user.put("pass_key",0000);

                    Map<String, Object> authorize = new HashMap<>();
                    authorize.put("device",device.getText().toString());
                    authorize.put("pass_reg",pass_reg.getText().toString());

                    Toast.makeText(register.this, "", Toast.LENGTH_SHORT).show();

                    db.collection("user").document(device.getText().toString())
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("MyActivity", "Registration Successful");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("MyActivity", "Registration Failure", e);
                                }
                            });

                    db.collection("authorize").document(device.getText().toString())
                            .set(authorize)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("MyActivity", "Registration Successful");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("MyActivity", "Registration Failure", e);
                                }
                            });
                    startActivity(i3);
                }
                else
                    Toast.makeText(register.this, "Kindly Fill in All the Fields Properly", Toast.LENGTH_LONG).show();
            }
        });
    }
}
