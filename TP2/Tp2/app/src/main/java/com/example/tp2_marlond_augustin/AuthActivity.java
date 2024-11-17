package com.example.tp2_marlond_augustin;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AuthActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_auth);
    }
    public String getUtilisateurToken(){
        SharedPreferences prefs=getSharedPreferences("UtilisateurToken",MODE_PRIVATE);

        return "SupperTokenIdentificationUtilisateur";
    }
}

