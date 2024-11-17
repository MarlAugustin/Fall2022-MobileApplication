package com.example.tp1_marlondaugustin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class reservationActivity extends AppCompatActivity {
    TextView tv_nomResto,tvPlacesRestantes,tv_nbPlaceReserver;
    EditText et_heureFin,et_phone,et_nom,et_date;
    SeekBar sb_nbReservation;
    Spinner sp_heureReserv;
    ArrayList<reservation> arrayListReservation=new ArrayList<>() ;
    String[] listeHeureReserv=new String[]{"16:00","17:30","19:00","20:30","22:00"};
    String[] listeHeureFinReserv=new String[]{"17:29","18:59","20:29","21:59","23:29"};
    private DatePickerDialog datePickerDialog;
    int nbPlace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        tv_nomResto=findViewById(R.id.tv_nomResto);
        tvPlacesRestantes=findViewById(R.id.tv_placesRestantes);
        tv_nbPlaceReserver=findViewById(R.id.tv_nbPlaceReserver);
        sb_nbReservation=findViewById(R.id.sb_nbReservation);
        sp_heureReserv=findViewById(R.id.sp_heureReserv);
        et_heureFin=findViewById(R.id.et_heureFin);
        et_phone=findViewById(R.id.et_phone);
        et_nom=findViewById(R.id.et_nom);
        et_date=findViewById(R.id.et_dateReservation);
        et_date.setText(getTodayData());
        Intent intentAffichage=getIntent();
        restaurant unRestaurant=intentAffichage.getParcelableExtra("restaurant");
        Bundle bReservation=intentAffichage.getExtras();
        nbPlace=unRestaurant.getNbPlacesRestantes();
        tv_nomResto.setText(unRestaurant.getNomRestaurant());
        if(Objects.equals(unRestaurant.getNomRestaurant(), getResources().getStringArray(R.array.listeRestaurant)[0])){
            arrayListReservation=(ArrayList<reservation>) bReservation.getSerializable("reservationRestaurant1");
        }
        if(Objects.equals(unRestaurant.getNomRestaurant(), getResources().getStringArray(R.array.listeRestaurant)[1])){
            arrayListReservation=(ArrayList<reservation>) bReservation.getSerializable("reservationRestaurant2");
        }
        ArrayAdapter<String> adapt_listeHeureReserv= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,listeHeureReserv);
        sp_heureReserv.setAdapter(adapt_listeHeureReserv);

        initDatePicker();
        sp_heureReserv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //ON change le contenu de et_heureFin selon la position du spinner
                et_heureFin.setEnabled(true);
                et_heureFin.setText(listeHeureFinReserv[i].toString());
                et_heureFin.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                et_heureFin.setEnabled(false);
            }
        });
        if(unRestaurant.nbPlacesRestantes>=5){
            tvPlacesRestantes.setTextColor(getResources().getColor(R.color.green));
        }if(unRestaurant.nbPlacesRestantes<5){
            tvPlacesRestantes.setTextColor(getResources().getColor(R.color.red));
        }
        tvPlacesRestantes.setText(nbPlace+" "+ getResources().getString(R.string.place_restantes));
        sb_nbReservation.setMax(10);
        sb_nbReservation.setProgress(0);
        tv_nbPlaceReserver.setText(sb_nbReservation.getProgress()+" "+ getResources().getString(R.string.place_reserver));
        sb_nbReservation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //On change tv_nbPlaceReserver selon la progression du nombre de réservation
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_nbPlaceReserver.setText(sb_nbReservation.getProgress()+" "+ getResources().getString(R.string.place_reserver));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tv_nbPlaceReserver.setText(sb_nbReservation.getProgress()+" "+ getResources().getString(R.string.place_reserver));
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_nbPlaceReserver.setText(sb_nbReservation.getProgress()+" "+ getResources().getString(R.string.place_reserver));
            }
        });

    }

    private String getTodayData() {
        /*
        Lien de la vidéo pour créer le calendrier qui pop-up
        https://www.youtube.com/watch?v=qCoidM98zNk
        */
        Calendar cal= Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        month+=1;
        int day=cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }

    public void onClickBtn_Reserv(View view){
        Boolean valide=false;
        Log.d("etiquette Nom",""+et_nom.getText().toString());
        if(sb_nbReservation.getProgress()<=nbPlace && sb_nbReservation.getProgress()!=0 && !et_nom.getText().toString().equals("") && isCellphoneValid(et_phone.getText().toString())==true
                && !et_date.getText().toString().equals("")){
            valide=true;
        }
        if(sb_nbReservation.getProgress()==0){
            Toast.makeText(this, getResources().getText(R.string.pas_de_reservation).toString(), Toast.LENGTH_SHORT).show();
            valide=false;
        }
        if(sb_nbReservation.getProgress()>nbPlace){
            Toast.makeText(this, getResources().getText(R.string.manque_de_place).toString(), Toast.LENGTH_SHORT).show();
            valide=false;
        }
        if(isCellphoneValid(et_phone.getText().toString())==false){
            Toast.makeText(this, getResources().getText(R.string.format_numero_telephone).toString(), Toast.LENGTH_SHORT).show();
            valide=false;
        }
        if(valide==false){
            Toast.makeText(this, getResources().getText(R.string.champ_vide).toString(), Toast.LENGTH_SHORT).show();
        }
        if(valide==true)
        {
            reservation reservCourante=new reservation(arrayListReservation.size()+1,sb_nbReservation.getProgress(),et_date.getText().toString(),
                    sp_heureReserv.getSelectedItem().toString() ,et_heureFin.getText().toString(),et_nom.getText().toString(),et_phone.getText().toString());
            arrayListReservation.add(reservCourante);
            Toast.makeText(this, getResources().getText(R.string.reservation_sauvegarder).toString(), Toast.LENGTH_SHORT).show();
            //On vide tous les champs et on actualise le nombre de places restantes
            et_nom.setText("");
            et_phone.setText("");
            initDatePicker();
            nbPlace=nbPlace-sb_nbReservation.getProgress();
            et_date.setText(getTodayData());
            sp_heureReserv.setSelection(0);
            sb_nbReservation.setProgress(0);
            et_heureFin.setEnabled(true);
            et_heureFin.setText(listeHeureFinReserv[0].toString());
            et_heureFin.setEnabled(false);
            tvPlacesRestantes.setText(nbPlace+" "+getString(R.string.place_restantes));
            if(nbPlace>=5){
                tvPlacesRestantes.setTextColor(getResources().getColor(R.color.green));
            }if(nbPlace<5){
                tvPlacesRestantes.setTextColor(getResources().getColor(R.color.red));
            }
            Log.d("Info réservation","Numéro de réservation "+reservCourante.noReservation
                    +"), Nombre de places: "+reservCourante.nbPlace+" Date de réservation"+reservCourante.dateReservation+" "
                    +reservCourante.getBlocReservationDebut()+" à "+ reservCourante.getBlocReservationFin()+" de "+ reservCourante.getNbPlace());
        }
    }
    @Override
    public void onBackPressed(){
        //Lorsqu'on quitte l'activité, on transfert toutes les nouvelles informations dans le main activity
        Intent intentAffichage=getIntent();
        Bundle bReservation=intentAffichage.getExtras();
        Intent retour=new Intent();
        Bundle b_ajout_reservation=new Bundle();
        ArrayList<reservation> reserv_resto1=new ArrayList<reservation>();
        ArrayList<reservation> reserv_resto2=new ArrayList<reservation>();
        if(Objects.equals(tv_nomResto.getText().toString(), getResources().getStringArray(R.array.listeRestaurant)[0])){
            reserv_resto1=arrayListReservation;
            reserv_resto2=(ArrayList<reservation>) bReservation.getSerializable("reservationRestaurant2");
        }
        if(Objects.equals(tv_nomResto.getText().toString(), getResources().getStringArray(R.array.listeRestaurant)[1])){
            reserv_resto1=(ArrayList<reservation>) bReservation.getSerializable("reservationRestaurant1");
            reserv_resto2=arrayListReservation;
        }
        b_ajout_reservation.putInt("placeRestantes",nbPlace);
        b_ajout_reservation.putSerializable("reservationRestaurant1", (Serializable) reserv_resto1);
        b_ajout_reservation.putSerializable("reservationRestaurant2", (Serializable) reserv_resto2);
        retour.putExtras(b_ajout_reservation);
        setResult(2,retour);
        finish();
    }

    private boolean isCellphoneValid(String numeroTelephone){
        //On vérifie si le numero de Telephone que l'usager a écrit respecte le format demandé
        boolean valid=false;
        String regex = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numeroTelephone);
        valid= matcher.matches();
        return valid;
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                String date=makeDateString(day,month,year);
                et_date.setText(date);
            }
        };
        Calendar cal= Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);

        //int style= AlertDialog;
        datePickerDialog=new DatePickerDialog(this,dateSetListener,year,month,day);
    }

    private String makeDateString(int day, int month, int year) {
        if(month<10&&day>9){
            return year+"-0"+month+"-"+day;
        }if(day<10&&month<10){
            return  year+"-0"+month+"-0"+day;
        }if(day<10&&month>9){
            return year+"-"+month+"-0"+day;
        }
        else{
            return year+"-"+month+"-"+day;
        }
    }

    private String getMonthFormat(int month) {
        if(month==1){
            return  "JAN";
        }
        if(month==2){
            return  "FEV";
        }if(month==3){
            return  "MAR";
        }if(month==4){
            return  "APR";
        }if(month==5){
            return  "JUN";
        }if(month==6){
            return  "JUL";
        }if(month==7){
            return  "AUG";
        }if(month==8){
            return  "JAN";
        }if(month==9){
            return  "SEP";
        }if(month==10){
            return  "OCT";
        }if(month==11){
            return  "NOV";
        }
        if(month==12){
            return  "DEC";
        }
        return "Jan";
    }

    public void onClickOpenDatePicker(View view) {
        datePickerDialog.show();
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        /*
        Cette méthode permet de créer un bundle qui contiendra les informations
        voulues
        */
        super.onSaveInstanceState(outState);
        outState.putInt("monCompteur",nbPlace);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        /*
        Cette méthode permet de trouver les informations d'un bundle
         à partir de sa clée
        */
        super.onRestoreInstanceState(savedInstanceState);
        nbPlace=savedInstanceState.getInt("monCompteur");
        tvPlacesRestantes.setText(nbPlace+" "+getString(R.string.place_restantes));
    }

}