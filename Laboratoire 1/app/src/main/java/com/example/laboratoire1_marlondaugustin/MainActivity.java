package com.example.laboratoire1_marlondaugustin;

import androidx.annotation.ColorRes;
import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {
    EditText et_courriel,et_password;
    CheckBox chk_afficher;
    Button   btn_valider;
    TextView tv_valide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_courriel=findViewById(R.id.et_courriel);
        et_password=findViewById(R.id.et_motDePasse);
        chk_afficher=findViewById(R.id.chk_afficher);
        btn_valider=findViewById(R.id.btn_valider);
        tv_valide=findViewById(R.id.tv_valide);
        tv_valide.setVisibility(View.INVISIBLE);
        tv_valide.setTextColor(getResources().getColor(R.color.red));
        tv_valide.setText(getResources().getText(R.string.champInvalide));
    }
    public void onClickChk_Afficher(View view){
        /*
            Lien pour voir les méthodes pour afficher et masquer
            un mot de passe.
            https://www.tutorialkart.com/kotlin-android/android-show-hide-password-in-edittext/
         */
        if(chk_afficher.isChecked()){
            et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else{
            et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
    }

    public void onClickBtn_Valider(View view){
        int nbLettreMajuscule=0,nbLettreMinuscule=0,nbCharSpeciaux=0;
        //List<Character> charValide = new ArrayList<Character>();
        String[] charValide={"@","#","$", "%", "&","(",")","[","]","{","}","_","=","<",">","+" ,
                "-","!", "?", "*","/", "|", ":", ";" ,"." ,"," ,"‘","\"", "~", "^","*"};
        tv_valide.setVisibility(View.VISIBLE);
        boolean emailValide=isEmailValid(et_courriel.getText().toString());

        /*
        J'ai du importer la bibliothèque Array pour pouvoir tester si le tableau charValide contient
        aux moins 1 char qui est spéciaux
         */
        if(et_password.getText().toString().length()>=10 && emailValide==true){

            for(int i=0;i<et_password.getText().toString().length();i++){
                if(Character.isUpperCase(et_password.getText().charAt(i))) {
                    nbLettreMajuscule++;
                }
                if(Character.isLowerCase(et_password.getText().charAt(i))){
                    nbLettreMinuscule++;
                }
                if(Arrays.toString(charValide).contains((""+et_password.getText().charAt(i)))==true){
                    nbCharSpeciaux++;
                }

            }
            if(nbCharSpeciaux>=1&&nbLettreMajuscule>=1&&nbLettreMinuscule>=1){
                tv_valide.setTextColor(getResources().getColor(R.color.blue));
                tv_valide.setText(getResources().getText(R.string.champValide));
            }else{
                tv_valide.setTextColor(getResources().getColor(R.color.red));
                tv_valide.setText(getResources().getText(R.string.champInvalide));
            }
        }else{
            tv_valide.setTextColor(getResources().getColor(R.color.red));
            tv_valide.setText(getResources().getText(R.string.champInvalide));
        }
    }
    /*
    Cette méthode reçoit un String email en paramètre
    On utilise regex pour voir si email convient aux caractéristiques d'un email
     */
    private boolean isEmailValid(String email){
        boolean valid=false;
        String regex = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        valid= matcher.matches();
        return valid;
    }
}