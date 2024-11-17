package com.example.tp2_marlond_augustin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class GestionActivity extends AppCompatActivity {
    Button btn_gestionProfil,btn_deconnecter,btn_article,btn_lecture_article;
    TextView tv_salutation;
    FirebaseAuth bdAuth;
    ImageView iv_profil;
    FirebaseUser usager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);
        bdAuth= FirebaseAuth.getInstance();
        tv_salutation=findViewById(R.id.tv_salutation);
        iv_profil=findViewById(R.id.iv_profil);
        /*
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        Uri selectedImage=Uri.parse("android.resource://com.example.tp2_marlond_augustin/drawable/goku");
        builder.setPhotoUri(selectedImage);
        builder.build();
        UserProfileChangeRequest request = builder.build();
        usager.updateProfile(request);
        */
        bdAuth.getCurrentUser().reload().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                /*
                    Pour un reload fonctionnel voici le lien
                    https://stackoverflow.com/questions/41488726/get-updated-user-from-firebase-authentication
                 */
                usager=bdAuth.getCurrentUser();
                tv_salutation.setText(""+getText(R.string.salutation)+" "+usager.getDisplayName().toString());
                if (ContextCompat.checkSelfPermission(GestionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                {
                    // Save your data in here
                    iv_profil.setImageURI(usager.getPhotoUrl());
                    //https://stackoverflow.com/questions/46843743/java-lang-securityexception-after-press-a-button-to-upload-a-image-to-server   User:Saneesh
                }
                else
                {
                    ActivityCompat.requestPermissions(GestionActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    Toast.makeText(GestionActivity.this, "need permission", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_gestionProfil=findViewById(R.id.btn_gestionProfil);
        btn_deconnecter=findViewById(R.id.btn_deconnecter);
        btn_article=findViewById(R.id.btn_article);
        btn_lecture_article=findViewById(R.id.btn_lire_article);
        btn_gestionProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GestionActivity.this,GestionProfilActivity.class);
                finish();
                startActivity(intent);

            }
        });
        btn_deconnecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bdAuth.signOut();
                Intent intent=new Intent(GestionActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GestionActivity.this,ArticleActivity.class);
                startActivity(intent);
            }
        });
        btn_lecture_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GestionActivity.this,LectureArticleActivity.class);
                startActivity(intent);
            }
        });
    }
}