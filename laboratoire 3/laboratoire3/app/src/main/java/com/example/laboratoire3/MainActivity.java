package com.example.laboratoire3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    TextView tv_texte_recu;
    EditText et_texte_inserer;
    Button btn_decrypter,btn_crypter;
    Toolbar toolbar;
    int cleCryptage=0;
    private String nomFichier="messageChiffrement.txt";
    private String chemin="'MesDocuments";
    File monFichierExterne;
    String mesDonnees="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<Character> listLettre=new ArrayList<>();
        listLettre.addAll(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_texte_recu=findViewById(R.id.tv_texte_recu);
        et_texte_inserer=findViewById(R.id.et_texte_inserer);
        btn_crypter=findViewById(R.id.btn_crypter);
        btn_decrypter=findViewById(R.id.btn_decrypter);
        btn_crypter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               tv_texte_recu.setText("");
               String texte=et_texte_inserer.getText().toString();
               String messageCrypter="";
               for(int i=0;i<texte.length();i++){
                   int indexLettre=listLettre.indexOf(texte.toLowerCase().charAt(i));
                   int positionDecalage=(indexLettre+cleCryptage);
                   int moduloPositionDecalage=positionDecalage%26;
                   if(listLettre.contains(texte.toLowerCase().charAt(i))){
                       //On vérifie que le charactère est une lettre
                        if(Character.isUpperCase(texte.charAt(i))){
                            //Si elle était une majuscule on Uppercase la position décaler
                            messageCrypter+=Character.toUpperCase(listLettre.get(moduloPositionDecalage));
                        }else{
                            //On ajoute la lettre minuscule
                            messageCrypter+=listLettre.get(moduloPositionDecalage);
                        }
                   }else{
                       //Si le charactère n'est pas une lettre on ne change pas la valeur
                       messageCrypter+= texte.charAt(i);
                   }
               }
               tv_texte_recu.setText(messageCrypter);
            }
        });
        btn_decrypter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_texte_recu.setText("");
                String texte=et_texte_inserer.getText().toString();
                String messageCrypter="";
                for(int j=0;j<texte.length();j++){
                    int indexLettre=listLettre.indexOf(texte.toLowerCase().charAt(j));
                    int positionDecalage=indexLettre-cleCryptage;
                    int moduloPositionDecalage=mod(positionDecalage,26);
                    //Log.d("indexLettre","position Index"+indexLettre);
                    //Log.d("indexDecalage", "position decalage"+positionDecalage+"e");
                    if(listLettre.contains(texte.toLowerCase().charAt(j))){
                        //On vérifie que le charactère est une lettre
                        if(Character.isUpperCase(texte.charAt(j))){
                            //Si elle était une majuscule on Uppercase la position décaler
                            messageCrypter+=Character.toUpperCase(listLettre.get(moduloPositionDecalage));
                        }else{
                            //On ajoute la lettre minuscule
                            messageCrypter+=listLettre.get(moduloPositionDecalage);
                        }
                    }else{
                        //Si le charactère n'est pas une lettre on ne change pas la valeur
                        messageCrypter+= texte.charAt(j);
                    }
                }
                tv_texte_recu.setText(messageCrypter);
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
                Intent intentFav=new Intent(this,activity_cle_decalage.class);
                Bundle bundle=new Bundle();
                bundle.putInt("decalage",cleCryptage);
                intentFav.putExtras(bundle);
                startActivityForResult(intentFav,1);
                return true;
            case R.id.mnu_ouvrir:
                FileInputStream fichierIn=null;
                try{
                    monFichierExterne= new File(getExternalFilesDir(chemin),nomFichier);
                    fichierIn=new FileInputStream(monFichierExterne);
                    DataInputStream streamReader=new DataInputStream(fichierIn);
                    BufferedReader bufferReader=new BufferedReader(new InputStreamReader(streamReader));
                    String ligne;
                    while((ligne= bufferReader.readLine())!=null){
                        mesDonnees=mesDonnees+ligne;
                    }
                    streamReader.close();
                } catch(FileNotFoundException e){
                    e.printStackTrace();
                } catch(IOException e){
                    e.printStackTrace();
                }
                et_texte_inserer.setText(mesDonnees);
                return true;
            case R.id.mnu_quitter:
                System.exit(0);
                return true;
            case R.id.mnu_sauvegarder:
                if(!stockageExterneDisponible()||stockageExterneLectureSeule()){
                    Toast.makeText(this, "Le chemin n'existe pas", Toast.LENGTH_SHORT).show();
                }else{
                    monFichierExterne= new File(getExternalFilesDir(chemin),nomFichier);
                    nomFichier="messageChiffrement.txt";
                    try{
                        FileOutputStream fichierOut=new FileOutputStream(monFichierExterne);
                        fichierOut.write(tv_texte_recu.getText().toString().getBytes());
                        fichierOut.close();
                        Toast.makeText(MainActivity.this, "Votre message à été sauvegarder"+nomFichier+" sauvegarder", Toast.LENGTH_SHORT).show();
                        et_texte_inserer.setText("");
                        tv_texte_recu.setText("");
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
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
        outState.putInt("cle_crypter",cleCryptage);
        outState.putString("texteInsérer",et_texte_inserer.getText().toString());
        outState.putString("code_cesar",tv_texte_recu.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        /*
        Cette méthode permet de trouver les informations d'un bundle
         à partir de sa clée
        */
        super.onRestoreInstanceState(savedInstanceState);
        cleCryptage=savedInstanceState.getInt("cle_crypter");
        et_texte_inserer.setText(savedInstanceState.getString("texteInsérer"));
        tv_texte_recu.setText(savedInstanceState.getString("code_cesar"));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        /*

         */
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==2){
            Bundle bundle=data.getExtras();
            cleCryptage=bundle.getInt("decalage");
        }
    }
    private static boolean stockageExterneLectureSeule(){
        String extStorageState= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)){
            return true;
        }
        return false;
    }
    private static boolean stockageExterneDisponible(){
        String extStorageState= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(extStorageState)){
            return true;
        }
        return false;
    }
    private int mod(int x, int y)
    {
        /*
        Vu que modulo négatif ne fonctionne pas,  voici ce qu'il faut faire pour corriger ça
        https://stackoverflow.com/questions/9202595/android-which-operator-for-modulo-doesnt-work-with-negative-numbers
         */
        int result = x % y;
        if (result < 0)
            result += y;
        return result;
    }
}