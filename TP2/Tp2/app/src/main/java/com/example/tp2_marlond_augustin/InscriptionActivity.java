package com.example.tp2_marlond_augustin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class InscriptionActivity extends AppCompatActivity {
    TextInputEditText tiet_nom,tiet_courriel,tiet_mdp,tiet_mdpConfirmer;
    FirebaseAuth bdAuth;
    Button btn_enregistrement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        tiet_courriel=findViewById(R.id.tiet_courriel);
        tiet_mdp=findViewById(R.id.tiet_mdp);
        tiet_nom=findViewById(R.id.tiet_nom);
        tiet_mdpConfirmer=findViewById(R.id.tiet_confirmer_mdp);
        btn_enregistrement=findViewById(R.id.btn_enregistrement);
        btn_enregistrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bdAuth=FirebaseAuth.getInstance();
                String courriel=tiet_courriel.getText().toString();
                String mdp=tiet_mdp.getText().toString();
                String mdp_confirmation=tiet_mdpConfirmer.getText().toString();
                String nom=tiet_nom.getText().toString();
                boolean nomValide=nomValide(nom);
                if(Patterns.EMAIL_ADDRESS.matcher(courriel).matches()){
                    if(mdp.matches(mdp_confirmation) && mdp.length()>=10&&nomValide==true){
                        InputMethodManager imm=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
                        bdAuth.createUserWithEmailAndPassword(courriel,mdp)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                                /*
                                                Allez dans la section mettre à jour
                                                https://firebase.google.com/docs/auth/android/manage-users
                                                 */
                                                bdAuth.signInWithEmailAndPassword(courriel, mdp).addOnCompleteListener(
                                                        new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                FirebaseUser usager=bdAuth.getCurrentUser();
                                                                UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
                                                                builder.setDisplayName(nom);
                                                                Uri uri=Uri.parse("android.resource://com.example.tp2_marlond_augustin/drawable/goku");
                                                                builder.setPhotoUri(uri);
                                                                builder.build();
                                                                UserProfileChangeRequest request = builder.build();
                                                                usager.updateProfile(request);
                                                                bdAuth.getCurrentUser().reload()
                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void unused) {
                                                                                Intent intent=new Intent(InscriptionActivity.this,GestionActivity.class);
                                                                                startActivity(intent);
                                                                            }
                                                                        });
                                                            }
                                                        }
                                                );
                                        }else{
                                            Toast.makeText(InscriptionActivity.this,getString(R.string.erreur_auth),Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                });
                    }
                    else if(mdp.length()<10){
                        tiet_mdp.setError(getString(R.string.mdp_erreur));
                        tiet_mdp.requestFocus();
                    }else if(nomValide==false){
                        tiet_nom.setError(getString(R.string.tietnom_erreur));
                        tiet_nom.requestFocus();
                    }else{
                        tiet_mdpConfirmer.setError(getString(R.string.mdp_dif));
                        tiet_mdpConfirmer.requestFocus();
                    }
                }else{
                    tiet_courriel.setError(getString(R.string.tiet_emaill_erreur));
                    tiet_courriel.requestFocus();
                }
            }
        });
    }

    boolean nomValide(String nom){
        boolean valide=false;
        if(nom.length()>0){
            if(!Patterns.EMAIL_ADDRESS.matcher(nom).matches()){
                valide=true;
            }
        }
        return valide;
    }
}