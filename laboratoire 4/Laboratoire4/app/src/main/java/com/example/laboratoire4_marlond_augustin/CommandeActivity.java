package com.example.laboratoire4_marlond_augustin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CommandeActivity extends AppCompatActivity {
    TextView tv_nomClient,tv_cellulaireClient;
    Button btn_commander;
    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;
    String message,nomPlat,description="";
    NotificationManager msg_notification;
    Repas repasCourant;
    List<String> listDescriptions=new ArrayList<String>();
    List<String> listNomPlats=new ArrayList<String>();
    List<Commande>listeCommandes=new ArrayList<Commande>();
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        tv_nomClient=findViewById(R.id.tv_nomClient);
        tv_cellulaireClient=findViewById(R.id.tv_cellulaireClient);
        Intent intent=getIntent();
        tv_nomClient.setText(intent.getStringExtra("nomClient"));
        tv_cellulaireClient.setText(intent.getStringExtra("numeroCellulaireClient"));
        ListeFragment monFragment=new ListeFragment();
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fl_list, monFragment).commit();
        btn_commander=findViewById(R.id.btn_commander);
        msg_notification=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        creationCanalNotification("com.example.laboratoire4_marlond_augustin",""+R.string.app_name,"Exemple d'une notification");
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick_btn_commander(View vue){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int sendSmsPermission=
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.SEND_SMS);

            if(sendSmsPermission!= PackageManager.PERMISSION_GRANTED){
                this.requestPermissions(
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSION_REQUEST_CODE_SEND_SMS
                );
                return;
            }
        }
        this.envoyerSMS();
        //message(vue);
        Commande nouvelleCommande=new Commande(listeCommandes.size()+1,repasCourant.getNoRepas(),tv_nomClient.getText().toString(),tv_cellulaireClient.getText().toString()
        ,repasCourant.getNom().toString(),repasCourant.getPrix());
        listeCommandes.add(nouvelleCommande);
        afficherGRNotification(vue);

    }
    private void envoyerSMS(){
        //On envoie un sms
        String notel=this.tv_cellulaireClient.getText().toString();
        TextView tv_categorie=findViewById(R.id.tv_categorie);
        message=tv_categorie.getText().toString();
        nomPlat=nomPlats().toLowerCase();
        try{
            SmsManager smsManager=SmsManager.getDefault();
            smsManager.sendTextMessage(notel,null,"Votre commande: "+message+","+nomPlat,null,null);
            Toast.makeText(CommandeActivity.this,"Votre commande: "+message+","+nomPlat,Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(CommandeActivity.this,"Erreur d'envoi SMS",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
    String nomPlats(){
        //Pour trouver le nom du plats je n'ai pas eu le choix que de chercher
        //Le repas qui contient la description voulu
        TextView tv=findViewById(R.id.tv_description);
        String tmp="";
        description=tv.getText().toString();
        listDescriptions.add(description);
        String dataFichierJson=ouvrirFichierJson(CommandeActivity.this,"Repas.json");
        Gson gson=new Gson();
        Type listeEtudiantType=new TypeToken<List<Repas>>() { }.getType();
        List<Repas>listeRepas=gson.fromJson(dataFichierJson,listeEtudiantType);
        for(Repas repas:listeRepas){
            if(repas.getDescription().toString().equals(description)){
                tmp=repas.getNom();
                listNomPlats.add(tmp);
                repasCourant=repas;
            }
        }
        return tmp;
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
    /*
    @RequiresApi(api = Build.VERSION_CODES.O)
    void message(View vue){
        //On crée une notification
        //final Icon icone=Icon.createWithResource(this, R.drawable.ic_baseline_shopping_bag_24);
        String canalId="com.example.laboratoire4_marlond_augustin";
        Notification nbuilder = new Notification.Builder(this,canalId)
                .setContentTitle("Repas commandé:"+nomPlat)
                .setContentText(description)
                .setSmallIcon(R.drawable.ic_baseline_shopping_bag_24)
                .setChannelId(canalId)
                .build();
        msg_notification.notify(notificationId,nbuilder);
        notificationId+=1;
    }*/
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void afficherGRNotification(View vue) {
        String canalId="com.example.laboratoire4_marlond_augustin";
        final String CLE_GROUPE_NOTIFICATION="clé_groupe_notification";
        int notificationId=101,notificationOriginalId=100;
        int longueur=listeCommandes.size();
        for(int i=0;i<longueur;i++){
            Notification.Builder builder= null;
            builder = new Notification.Builder(this, canalId)
                        .setSmallIcon(R.drawable.ic_baseline_shopping_bag_24)
                        .setContentTitle("Repas commandé:" + listNomPlats.get(i))
                        .setContentText(listDescriptions.get(i))
                        .setGroup(CLE_GROUPE_NOTIFICATION);
                msg_notification.notify(notificationId,builder.build());
            notificationId+=1;
        }
        Notification.Builder builderSommaire = new Notification.Builder(this, canalId)
                .setSmallIcon(R.drawable.ic_baseline_shopping_bag_24)
                .setContentTitle("Repas commandé:" )
                .setContentText("Description")
                .setGroup(CLE_GROUPE_NOTIFICATION)
                .setGroupSummary(true);
        msg_notification.notify(notificationOriginalId,builderSommaire.build());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void creationCanalNotification(String id, String nom, String description) {
        //Nécesssaire pour la création des notifications
        int priorite=NotificationManager.IMPORTANCE_LOW;
        NotificationChannel canal=new NotificationChannel(id,nom,priorite);
        canal.setDescription(description);
        canal.enableLights(true);
        canal.setLightColor(Color.RED);
        msg_notification.createNotificationChannel(canal);
    }

}