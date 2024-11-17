package com.example.examen1_marlond_augustin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner sp_programme;
    List<String> listProgramme=new ArrayList<>();
    List<Etudiant> listEtudiant=new ArrayList<>();
    ListView lv_etudiant;
    List<String> listDa=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp_programme=findViewById(R.id.sp_programme);
        lv_etudiant=findViewById(R.id.lv_etudiant);
        listProgramme.add("420");
        listProgramme.add("243");
        listProgramme.add("410");
        listProgramme.add("4F5");
        listProgramme.add("650");
        listEtudiant.add(new Etudiant(1,"205455942","Marco","Paulo","2002-01-1","420"));
        listEtudiant.add(new Etudiant(2,"205455212","Oliver","King","2001-09-22","420"));
        listEtudiant.add(new Etudiant(3,"205455213","Samuel","King","2001-09-22","243"));
        ArrayAdapter<String> adaptateur=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listProgramme);
        sp_programme.setAdapter(adaptateur);
        sp_programme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                listDa.clear();
                for(int i=0;i<listEtudiant.size();i++){
                    if(listEtudiant.get(i).getProgramme()==sp_programme.getSelectedItem()){
                        listDa.add(listEtudiant.get(i).NumeroDA);
                    }
                }
                ArrayAdapter<String> adaptateur_etudiant= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,listDa);
                lv_etudiant.setAdapter(adaptateur_etudiant);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        lv_etudiant.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent affichageIntent=new Intent(getApplicationContext(),activity_etudiant.class);
                Etudiant etudiantChoisi=new Etudiant();
                //Log.d("TAG", ""+lv_etudiant.getItemAtPosition(position).toString());
                for(int i=0;i<listEtudiant.size();i++){
                    if(listEtudiant.get(i).getNumeroDA().toString()==lv_etudiant.getItemAtPosition(position).toString()){
                        etudiantChoisi=listEtudiant.get(i);
                    }
                }
                Bundle bundle=new Bundle();
                bundle.putParcelable("etudiant",etudiantChoisi);
                affichageIntent.putExtras(bundle);
                startActivity(affichageIntent);
            }
        });

    }
}