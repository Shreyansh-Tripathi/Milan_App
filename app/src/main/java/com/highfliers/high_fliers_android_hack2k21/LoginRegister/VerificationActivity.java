package com.highfliers.high_fliers_android_hack2k21.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.highfliers.high_fliers_android_hack2k21.R;

import java.util.ArrayList;
import java.util.HashMap;

public class VerificationActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        user= FirebaseAuth.getInstance().getCurrentUser();
        firestore=FirebaseFirestore.getInstance();
        Button button=findViewById(R.id.verify_btn);
        button.setOnClickListener(v -> {
            isVerified();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isVerified();
    }

    public void isVerified(){
        user.reload().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                boolean verified=user.isEmailVerified();

                if(verified){
                    HashMap<String,Object> map=new HashMap();
                    map.put("interests",new ArrayList<>());
                    map.put("name",getIntent().getStringExtra("name"));
                    firestore.collection("Users").document(user.getUid()).set(map);
                    Intent intent=new Intent(VerificationActivity.this, InterestActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
            else
                Toast.makeText(VerificationActivity.this, "Unsuccessful", Toast.LENGTH_SHORT).show();
        });
    }
}