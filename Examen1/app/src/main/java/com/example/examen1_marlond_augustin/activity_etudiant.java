package com.example.examen1_marlond_augustin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class activity_etudiant extends AppCompatActivity {
    TextView tv_nom,tv_prenom,tv_dateNaissance,tv_numero;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etudiant);
        tv_nom=findViewById(R.id.tv_nom);
        tv_prenom=findViewById(R.id.tv_prenom);
        tv_dateNaissance=findViewById(R.id.tv_dateNaissance);
        tv_numero=findViewById(R.id.tv_numero);
        imageView=findViewById(R.id.img_graduation);

        Intent intentAffichage=getIntent();
        Etudiant etudiant=intentAffichage.getParcelableExtra("etudiant");
        imageView.setImageResource(R.drawable.graduation);

        tv_dateNaissance.setText(etudiant.getDateNaissance());
        tv_nom.setText(etudiant.getNom());
        tv_prenom.setText(etudiant.getPrenom());
        tv_numero.setText(etudiant.getNumeroDA());
    }

    public void onClick_BtnSupprimer(View view) {

    }
}