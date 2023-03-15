package com.highfliers.high_fliers_android_hack2k21.LoginRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.highfliers.high_fliers_android_hack2k21.ChooseActivity;
import com.highfliers.high_fliers_android_hack2k21.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                changeActivity();
                finish();
            }
        },3000);
    }

    public void changeActivity(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent= new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else{
            if(!user.isEmailVerified()){
                Intent intent= new Intent(MainActivity.this, VerificationActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent= new Intent(MainActivity.this, ChooseActivity.class);
                startActivity(intent);
            }
        }
    }
}