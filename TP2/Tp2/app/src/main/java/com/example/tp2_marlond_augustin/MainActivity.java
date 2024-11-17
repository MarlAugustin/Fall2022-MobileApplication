package com.example.tp2_marlond_augustin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextInputEditText tiet_courriel_connexion,tiet_mdp_connexion;
    FirebaseAuth bdAuth;
    Button btn_connexion,btn_inscription;
    ImageView iv_logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bdAuth=FirebaseAuth.getInstance();
        tiet_courriel_connexion=findViewById(R.id.tiet_courriel_connexion);
        tiet_mdp_connexion=findViewById(R.id.tiet_mdp_connexion);
        btn_connexion=findViewById(R.id.btn_connexion);
        btn_inscription=findViewById(R.id.btn_inscription);
        iv_logo=findViewById(R.id.iv_logo);
        iv_logo.setImageDrawable(getDrawable(R.drawable.home_logo));
        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courriel=tiet_courriel_connexion.getText().toString();
                String motDePasse=tiet_mdp_connexion.getText().toString();

                //Il faudra tester si le compte existe valide est là juste pour voir si je peux ouvrir l'intent
                if(Patterns.EMAIL_ADDRESS.matcher(courriel).matches()&&motDePasse.length()>=10){
                       bdAuth.signInWithEmailAndPassword(courriel, motDePasse)
                               .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                   @Override
                                   public void onComplete(@NonNull Task<AuthResult> task) {
                                       if (task.isSuccessful()) {
                                           Intent intent = new Intent(MainActivity.this, GestionActivity.class);
                                           Bundle bundle = new Bundle();
                                           intent.putExtras(bundle);
                                           startActivity(intent);
                                           finish();
                                       }else{
                                           Toast.makeText(MainActivity.this, getString(R.string.account_erreur), Toast.LENGTH_SHORT).show();
                                       }
                                   }
                               });

                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(courriel).matches()){
                    tiet_courriel_connexion.setError(getString(R.string.couriel_erreur));
                    tiet_courriel_connexion.requestFocus();
                }
                else{
                    tiet_mdp_connexion.setError(getString(R.string.mdp_erreur));
                    tiet_mdp_connexion.requestFocus();                }

            }
        });
        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,InscriptionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}