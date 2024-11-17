package com.example.tp2_marlond_augustin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class GestionProfilActivity extends AppCompatActivity {
    TextInputEditText tiet_nom,tiet_courriel;
    FirebaseAuth bdAuth;
    ImageView iv_profil;
    Button btn_modifier;
    Uri selectedImage;
    String courriel;
    Boolean uriModifier=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_profil);
        tiet_courriel=findViewById(R.id.tiet_courriel);
        tiet_nom=findViewById(R.id.tiet_nom);
        iv_profil=findViewById(R.id.iv_profil);
        btn_modifier=findViewById(R.id.btn_mettreAJour);
        bdAuth=FirebaseAuth.getInstance();
        bdAuth.getCurrentUser()
                .reload()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        FirebaseUser usager=bdAuth.getCurrentUser();
                        iv_profil.setImageURI(usager.getPhotoUrl());
                        tiet_courriel.setText(usager.getEmail().toString());
                        tiet_nom.setText(usager.getDisplayName().toString());
                        iv_profil.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent,3);
                                //L'idée était que lorsqu'une personne clique sur l'image que ça donne l'option de choisir une
                                //autre image
                            }
                        });
                    }
                });
        btn_modifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courriel=tiet_courriel.getText().toString();
                boolean nomValide=nomValide(tiet_nom.getText().toString());
                if (Patterns.EMAIL_ADDRESS.matcher(courriel).matches()) {
                    if(nomValide==true) {
                        FirebaseUser usager=bdAuth.getCurrentUser();
                        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
                        builder.setDisplayName(tiet_nom.getText().toString());
                        if(uriModifier==true){
                            builder.setPhotoUri(selectedImage);
                            Toast.makeText(GestionProfilActivity.this,""+selectedImage,Toast.LENGTH_SHORT).show();
                        }
                        builder.build();
                        UserProfileChangeRequest request = builder.build();
                        usager.updateProfile(request);
                        usager.updateEmail(courriel);
                        bdAuth.getCurrentUser().reload()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(GestionProfilActivity.this,getString(R.string.profil_maj),Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(GestionProfilActivity.this,GestionActivity.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                });
                    }else{
                        tiet_nom.setError(getString(R.string.tietnom_erreur));
                        tiet_nom.requestFocus();
                    }
                }else{
                    tiet_courriel.setError(getString(R.string.tiet_emaill_erreur));
                    tiet_courriel.requestFocus();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        //https://www.youtube.com/watch?v=H1ja8gvTtBE
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK&& data!=null){
            selectedImage=data.getData();
            iv_profil.setImageURI(selectedImage);
            uriModifier=true;
        }
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