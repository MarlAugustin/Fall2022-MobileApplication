package com.example.tp1_marlondaugustin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class affichageActivity extends AppCompatActivity {
    TextView tv_Restaurant,tv_selection;
    Spinner sp_date;
    List<reservation> listeReservations=new ArrayList<>();
    List<reservation> listeReservationDateCourante=new ArrayList<reservation>();
    List<String> listDateDebut= new ArrayList<>();
    ListView lv_reservation;
    adaptateurReservation adaptateur_reservation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);
        sp_date=findViewById(R.id.spinner_date);
        lv_reservation=findViewById(R.id.lv_reservation);
        tv_Restaurant=findViewById(R.id.tv_nomResto2);
        Intent intentAffichage=getIntent();
        restaurant unRestaurant=intentAffichage.getParcelableExtra("restaurant");
        Bundle bReservation=intentAffichage.getExtras();
        tv_Restaurant.setText(unRestaurant.getNomRestaurant());
        if(Objects.equals(unRestaurant.getNomRestaurant(), getResources().getStringArray(R.array.listeRestaurant)[0])){
            listeReservations=(ArrayList<reservation>) bReservation.getSerializable("reservationRestaurant1");
        }
        if(Objects.equals(unRestaurant.getNomRestaurant(), getResources().getStringArray(R.array.listeRestaurant)[1])){
            listeReservations=(ArrayList<reservation>) bReservation.getSerializable("reservationRestaurant2");
        }
        tv_selection=findViewById(R.id.tv_selection);

        for(int i=0;i<listeReservations.size();i++){
            //On ajoute toutes les dates dans la listDateDebut
            listDateDebut.add(listeReservations.get(i).getDateReservation().toString());
        }
        for(int j=0;j<listDateDebut.size();j++){
            /* On vérifie si une date apparaît une seule fois, sinon on l'efface à l'indice de sa dernière
            apparition*/
            int nbApparition=0,indiceDerniereApparition=0;
            for(int k=0;k<listDateDebut.size();k++){
                if(listDateDebut.get(j).toString().equals(listDateDebut.get(k).toString())){
                    nbApparition++;
                    indiceDerniereApparition=k;
                }
            }
            if(nbApparition>1){
                while(nbApparition>1) {
                    listDateDebut.remove(listDateDebut.lastIndexOf(listDateDebut.get(j).toString()));
                    nbApparition--;
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            trierTableauDate(listDateDebut);
        }
        ArrayAdapter<String> adaptateur=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listDateDebut);
        sp_date.setAdapter(adaptateur);
        sp_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                //On efface les données contenu dans listeReservationDateCourante
                //Ensuite on ajoute toute les réservations qui ont la date sélectionné
                listeReservationDateCourante.clear();
                for(int k=0;k<listeReservations.size();k++){
                    if(listDateDebut.get(position).toString().equals(listeReservations.get(k).getDateReservation().toString())){
                        listeReservationDateCourante.add(listeReservations.get(k));
                    }
                }
                trierTableauReservation(listeReservationDateCourante);
                adaptateur_reservation=new adaptateurReservation(getApplicationContext(),listeReservationDateCourante);
                lv_reservation.setAdapter(adaptateur_reservation);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        adaptateur_reservation=new adaptateurReservation(getApplicationContext(),listeReservationDateCourante);
        lv_reservation.setAdapter(adaptateur_reservation);
        lv_reservation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Lorsqu'on clique sur une réservation, on affiche via un message Toast les informations qui
                //n'étaient pas indiqué sur l'activité.
                reservation reservationSelectionne= listeReservations.get(i);
                Toast.makeText(adapterView.getContext(),getResources().getString(R.string.numero_de_reservation).toString()+reservationSelectionne.getNoReservation()+"\n"
                        + getResources().getString(R.string.numero_tel).toString()+": "+reservationSelectionne.getTelPersonne(),Toast.LENGTH_LONG).show();
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void trierTableauDate(List<String> listDates){
        //Cette méthode est utilisé pour trier les données qui sont contenu.
        // On arrange le tableau d'un ordre croissant
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            for (int i = 0; i < listDates.size(); i++) {
                int posmin = i;
                Date date = format.parse(listDates.get(posmin).toString());
                for (int j = i + 1; j < listDates.size(); j++) {
                    Date prochaineDate = format.parse(listDates.get(j).toString());
                    if (prochaineDate.before(format.parse(listDates.get(posmin).toString()))) {
                        posmin = j;
                    }
                }
                if (posmin != i) {
                    //Si la position minimum est différente
                    String temp = listDates.get(i).toString();
                    listDates.set(i, listDates.get(posmin).toString());
                    listDates.set(posmin, temp);
                }
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    private void trierTableauReservation(List<reservation> listReservDateCourante){
        //Cette méthode est utilisé pour trier les données qui sont contenu
        //On trie les réservations selon le début de leur réservation
        ArrayList<String>listeHeureReserv=new ArrayList<String>();
        listeHeureReserv.add("16:00");
        listeHeureReserv.add("17:30");
        listeHeureReserv.add("19:00");
        listeHeureReserv.add("20:30");
        listeHeureReserv.add("22:00");
        for(int i=0;i<listReservDateCourante.size();i++){
            int posmin=i;
            for(int j=i+1;j<listReservDateCourante.size();j++){
                String prochainBlocReservation = listReservDateCourante.get(j).getBlocReservationDebut().toString();
                if(listeHeureReserv.indexOf(prochainBlocReservation)<
                        listeHeureReserv.indexOf(listReservDateCourante.get(posmin).getBlocReservationDebut().toString())){
                    posmin=j;
                }
            }
            if(posmin!=i){
                //Si la position minimum est différente
                reservation champActuelle=listReservDateCourante.get(i);
                reservation plusPetit=listReservDateCourante.get(posmin);
                listReservDateCourante.set(i,plusPetit);
                listReservDateCourante.set(posmin,champActuelle);
            }
        }
    }



}