package com.example.laboratoire3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

public class activity_cle_decalage extends AppCompatActivity {
    Button btn_valider;
    EditText et_decalage;
    Toolbar tb_decalage;
    int decalage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cle_decalage);
        Intent intent=getIntent();
        decalage= (int) intent.getSerializableExtra("decalage");
        et_decalage=findViewById(R.id.et_decalage);
        btn_valider=findViewById(R.id.btn_valider);
        et_decalage.setText(""+decalage);
        btn_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int valeurTexte=Integer.valueOf(et_decalage.getText().toString());
                if(valeurTexte>=0&&valeurTexte<=25){
                    Toast.makeText(activity_cle_decalage.this, "La cle est valide", Toast.LENGTH_SHORT).show();
                    decalage=valeurTexte;
                }else{
                    Toast.makeText(activity_cle_decalage.this, "La cle doit être comprise entre 0 et 25", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mnu_decalage:
                return true;
            case R.id.mnu_ouvrir:
                return true;
            case R.id.mnu_quitter:
                System.exit(0);
                return true;
            case R.id.mnu_sauvegarder:
                Toast.makeText(this, "Sauvegarder", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        /*
        Cette méthode permet de créer un bundle qui contiendra les informations
        voulues
        */
        super.onSaveInstanceState(outState);
        outState.putInt("decalage",decalage);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        /*
        Cette méthode permet de trouver les informations d'un bundle
         à partir de sa clée
        */
        super.onRestoreInstanceState(savedInstanceState);
        decalage=savedInstanceState.getInt("decalage");
    }

    @Override
    public void onBackPressed() {
        Intent retour=new Intent();
        Bundle b_cle_decalage=new Bundle();
        b_cle_decalage.putInt("decalage",decalage);
        retour.putExtras(b_cle_decalage);
        setResult(2,retour);
        finish();
    }
}