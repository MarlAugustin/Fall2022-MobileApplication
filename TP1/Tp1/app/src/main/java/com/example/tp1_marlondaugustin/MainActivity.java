package com.example.tp1_marlondaugustin;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextView tv_titre,tv_placesRestantes;
    Button btn_reserver,btn_afficher;
    Spinner spin_Restaurant;
    List<restaurant> listeRestaurant=new ArrayList<>();
    List<reservation> listeReservationRestaurant1=  new ArrayList<>();
    List<reservation> listeReservationRestaurant2=  new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_titre = findViewById(R.id.tv_titre);
        tv_placesRestantes=findViewById(R.id.tv_placeRestantes);
        btn_reserver=findViewById(R.id.btn_reserver);
        btn_afficher=findViewById(R.id.btn_afficher);
        spin_Restaurant=findViewById(R.id.spin_restaurant);
        listeRestaurant.add(new restaurant(1,16,10,((String[]) getResources().getStringArray(R.array.listeRestaurant))[0]));
        listeRestaurant.add(new restaurant(2,20,4,((String[]) getResources().getStringArray(R.array.listeRestaurant))[1]));
        listeReservationRestaurant1.add(new reservation(1,4,"2022-10-21","20:30",
                "21:59","Uncle Ben","215-951-9999"));
        listeReservationRestaurant1.add(new reservation(2,4,"2022-12-25","16:00",
                "17:29","Marc Andree","439-932-1232"));
        listeReservationRestaurant1.add(new reservation(3,2,"2022-12-25","17:30",
                "18:59","Lebron James","482-429-9284"));
        listeReservationRestaurant2.add(new reservation(1,2,"2022-12-25","16:00",
                "17:29","Marco Paulo","832-329-232"));
        listeReservationRestaurant2.add(new reservation(2,2,"2022-12-25","17:30",
                "18:59","David Judas","777-777-7777"));
        listeReservationRestaurant2.add(new reservation(3,4,"2022-10-21","20:30",
                "21:59","Jean Marc","832-214-2821"));
        spin_Restaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /*
                On donne une couleur à tv_placesRestantes selon le nombre de places restantes.
                On set le texte
                */
                restaurant restaurantSelectionnee=listeRestaurant.get(position);
                if(restaurantSelectionnee.nbPlacesRestantes>=5){
                    tv_placesRestantes.setTextColor(getResources().getColor(R.color.green));
                }if(restaurantSelectionnee.nbPlacesRestantes<5){
                    tv_placesRestantes.setTextColor(getResources().getColor(R.color.red));
                }
                tv_placesRestantes.setText(restaurantSelectionnee.nbPlacesRestantes+" "+ getResources().getString(R.string.place_restantes));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        btn_reserver.setOnClickListener(new View.OnClickListener() {
            //On ouvre la page de réservation
            @Override
            public void onClick(View v) {
                Intent reservationIntent=new Intent(MainActivity.this,reservationActivity.class);
                Bundle bReservation=new Bundle();
                bReservation.putSerializable("reservationRestaurant1", (Serializable) listeReservationRestaurant1);
                bReservation.putSerializable("reservationRestaurant2", (Serializable) listeReservationRestaurant2);
                restaurant restaurantChoisi=listeRestaurant.get(spin_Restaurant.getSelectedItemPosition());
                reservationIntent.putExtra("restaurant",restaurantChoisi);
                reservationIntent.putExtras(bReservation);
                startActivityForResult(reservationIntent,1);

            }
        });

        btn_afficher.setOnClickListener(new View.OnClickListener() {
            //On ouvre la page d'affichage des réservations
            @Override
            public void onClick(View v) {
                Intent affichageIntent=new Intent(getApplicationContext(),affichageActivity.class);
                Bundle bReservation=new Bundle();
                bReservation.putSerializable("reservationRestaurant1", (Serializable) listeReservationRestaurant1);
                bReservation.putSerializable("reservationRestaurant2", (Serializable) listeReservationRestaurant2);
                restaurant restaurantChoisi=listeRestaurant.get(spin_Restaurant.getSelectedItemPosition());
                affichageIntent.putExtra("restaurant",restaurantChoisi);
                affichageIntent.putExtras(bReservation);
                startActivity(affichageIntent);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        /*
        On reçoit le tableau qui a eu un ajout ainsi que celui qui ne change pas.
        On actualise les tableaux listeReservationRestaurant1 et listeReservationRestaurant2
        On actualise le nombre de places restantes, selon le restaurant choisi.
        On affiche la nouvelle valeur de places restantes.
         */
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==2){
            int nbPlace=0;
            Bundle bundle=data.getExtras();
            List<reservation> listeReservResto1=  new ArrayList<>();
            List<reservation> listeReservResto2=  new ArrayList<>();
            nbPlace=bundle.getInt("placeRestantes");
            listeReservResto1=(ArrayList<reservation>) bundle.getSerializable("reservationRestaurant1");
            listeReservResto2=(ArrayList<reservation>) bundle.getSerializable("reservationRestaurant2");
            listeReservationRestaurant1=listeReservResto1;
            listeReservationRestaurant2=listeReservResto2;
            //Log.d("Longueur Tab1:",""+listeReservResto1.size());
            //Log.d("Longueur Tab2:",""+listeReservResto2.size());
            if(Objects.equals(spin_Restaurant.getSelectedItem().toString(), getResources().getStringArray(R.array.listeRestaurant)[0])){
                listeRestaurant.get(spin_Restaurant.getSelectedItemPosition()).setNbPlacesRestantes(nbPlace);
            }
            if(Objects.equals(spin_Restaurant.getSelectedItem().toString(), getResources().getStringArray(R.array.listeRestaurant)[1])){
                listeRestaurant.get(spin_Restaurant.getSelectedItemPosition()).setNbPlacesRestantes(nbPlace);
            }
            tv_placesRestantes.setText(nbPlace+" "+getResources().getString(R.string.place_restantes));
            if(nbPlace>=5){
                tv_placesRestantes.setTextColor(getResources().getColor(R.color.green));
            }if(nbPlace<5){
                tv_placesRestantes.setTextColor(getResources().getColor(R.color.red));
            }
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        /*
        Cette méthode permet de créer un bundle qui contiendra les informations
        voulues
        */
        super.onSaveInstanceState(outState);
        outState.putSerializable("listeRestaurant",(Serializable) listeRestaurant);
        outState.putSerializable("listeReservationRestaurant1",(Serializable) listeReservationRestaurant1);
        outState.putSerializable("listeReservationRestaurant2",(Serializable) listeReservationRestaurant2);

    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        /*
        Cette méthode permet de trouver les informations d'un bundle
         à partir de sa clée
        */
        super.onRestoreInstanceState(savedInstanceState);
        listeRestaurant=(ArrayList<restaurant>)savedInstanceState.getSerializable("listeRestaurant");
        listeReservationRestaurant1=(ArrayList<reservation>)savedInstanceState.getSerializable("listeReservationRestaurant1");
        listeReservationRestaurant2=(ArrayList<reservation>)savedInstanceState.getSerializable("listeReservationRestaurant2");
    }
}