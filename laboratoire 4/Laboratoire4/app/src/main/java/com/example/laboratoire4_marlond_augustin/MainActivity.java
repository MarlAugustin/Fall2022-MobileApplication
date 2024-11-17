package com.example.laboratoire4_marlond_augustin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btn_menu;
    EditText et_nom,et_cellulaire;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_menu=findViewById(R.id.btn_menu);
        et_nom=findViewById(R.id.et_nom);
        et_cellulaire=findViewById(R.id.et_cellulaire);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom=et_nom.getText().toString();
                String numeroCellulaire=et_cellulaire.getText().toString();
                Intent intentCommande=new Intent(MainActivity.this,CommandeActivity.class);
                intentCommande.putExtra("nomClient",nom);
                intentCommande.putExtra("numeroCellulaireClient",numeroCellulaire);
                startActivity(intentCommande);
            }
        });
    }
}