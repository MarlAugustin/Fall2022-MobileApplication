package com.example.examen2_marlond_augustin_final;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btn_import,btn_inscription;
    Spinner sp_session;
    ArrayList<String> sessions=new ArrayList<>();
    List<cours> listeCours=new ArrayList<cours>();
    ImageView iv_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_import=findViewById(R.id.btn_import);
        btn_inscription=findViewById(R.id.btn_inscription);
        sp_session=findViewById(R.id.sp_session);
        iv_image=findViewById(R.id.iv_image);
        iv_image.setImageResource(R.drawable.noun_elearning_1316718);
        sessions.add("Automne 2022");
        sessions.add("Hiver 2023");
        ArrayAdapter adapter=new ArrayAdapter<String>(getLayoutInflater().getContext(),android.R.layout.simple_spinner_dropdown_item,sessions);
        sp_session.setAdapter(adapter);
        btn_inscription.setEnabled(false);
        btn_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sp_session.getSelectedItemPosition()!=-1){
                    String dataFichierJson;
                    if(sp_session.getSelectedItemPosition()==0){
                         dataFichierJson=ouvrirFichierJson(MainActivity.this,"dataExaA_Aut22.json");
                    }else{
                         dataFichierJson=ouvrirFichierJson(MainActivity.this,"dataExaA_Hiv23.json");
                    }
                    Gson gson=new Gson();
                    Type listeEtudiantType=new TypeToken<List<cours>>() { }.getType();
                    listeCours=gson.fromJson(dataFichierJson,listeEtudiantType);
                    //Log.d("Size",""+listeCours.size());
                    btn_inscription.setEnabled(true);
                }else{
                    Toast.makeText(MainActivity.this,"Vous choisir une session",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AffichageActivity.class);
                Bundle bundle=new Bundle();
                for(int i=0;i<listeCours.size();i++){
                    //J'avais oublié comment faire pour envoyer une listecontenant des parcelables, donc j'ai envoyé chacun
                    //des cours de la session
                    bundle.putParcelable("cours"+(i+1),listeCours.get(i));
                }
                intent.putExtras(bundle);
                intent.putExtra("session",sp_session.getSelectedItem().toString());
                startActivity(intent);
            }
        });
    }

    static String ouvrirFichierJson(Context contexte, String nomFichier){
        //On ouvre et on affiche les données dans le textView
        String stringJson;
        try{
            InputStream in=contexte.getAssets().open(nomFichier);

            int taille=in.available();
            byte[]tampon=new byte[taille];
            in.read(tampon);
            in.close();

            stringJson=new String(tampon,"UTF-8");

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return stringJson;
    }
}