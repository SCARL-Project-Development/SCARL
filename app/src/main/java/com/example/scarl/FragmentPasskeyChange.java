package com.example.scarl;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class FragmentPasskeyChange extends Fragment {
    SharedPreferences shrdprf;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passkeychange,container,false);

        final String logindev=getArguments().getString("logindev");

        final EditText new_key=view.findViewById(R.id.new_key);
        final EditText re_key=view.findViewById(R.id.re_key);

        shrdprf= Objects.requireNonNull(getActivity()).getSharedPreferences("MyPrf", MODE_PRIVATE);

        Button pass_button = view.findViewById(R.id.pass_button);
        pass_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new_key.getText().toString().equals(re_key.getText().toString()))
                {

        //            final String logindev=shrdprf.getString("DevNo","");
          //          Toast.makeText(getContext(), logindev, Toast.LENGTH_LONG).show();
                    final DocumentReference docRef = db.collection("user").document(logindev);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                                    Map<String, Object> user = document.getData();
                                    user.put("pass_key",new_key.getText().toString());
                                    db.collection("user").document(logindev)
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("MyActivity", "Passkey Updated");
                                                    Toast.makeText(getContext(), "Passkey Updated", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("MyActivity", "Passkey update failure", e);
                                                    Toast.makeText(getContext(), "Passkey update failure", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            }
                            else {
                                Log.d("TAG", "No such document");
                                Toast.makeText(getContext(),"No such document",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getContext(), "Passkeys don't match!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}
