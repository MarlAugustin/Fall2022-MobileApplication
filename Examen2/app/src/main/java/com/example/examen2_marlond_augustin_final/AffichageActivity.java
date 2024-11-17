package com.example.examen2_marlond_augustin_final;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class AffichageActivity extends AppCompatActivity {
    TextView tv_choix;
    ListView lv_cours;
    FrameLayout frm_description;
    EditText et_nom;
    List<cours> listeCours=new ArrayList<>();
    List<String>listeCodesCours=new ArrayList<>();
    Button btn_inscription;
    int positionCours;
    public static final String  CONST_NOM_FIC="inscriptions";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage);
        tv_choix=findViewById(R.id.tv_choix);
        lv_cours=findViewById(R.id.lv_cours);
        frm_description=findViewById(R.id.frm_description);
        et_nom=findViewById(R.id.et_nom);
        btn_inscription=findViewById(R.id.btn_inscription_cours);
        Intent intent=getIntent();
        String session=intent.getStringExtra("session");
        Bundle bundle=intent.getExtras();
        tv_choix.setText("Choix de cours: "+session);
        for(int i=0;i<7;i++){
            cours courActuelle=bundle.getParcelable("cours"+(i+1));
            listeCours.add(courActuelle);
            listeCodesCours.add(courActuelle.getCodeCours());
        }
        ArrayAdapter adapter=new ArrayAdapter<String>(getLayoutInflater().getContext(),android.R.layout.simple_list_item_1,listeCodesCours);
        lv_cours.setAdapter(adapter);
        lv_cours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                lv_cours.setSelector(R.color.grey);
                Bundle bundle=new Bundle();
                bundle.putParcelable("cours",listeCours.get(position));
                DescriptionFragment descriptionFragment=new DescriptionFragment();
                descriptionFragment.setArguments(bundle);
                FragmentManager fm=getSupportFragmentManager();
                fm.beginTransaction().replace(frm_description.getId(), descriptionFragment).commit();
                positionCours=position;
            }
        });
        btn_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_nom.getText().toString().equals("")){
                    inscription();
                    Toast.makeText(AffichageActivity.this,""+et_nom.getText().toString()+" votre inscription a été réussie",Toast.LENGTH_SHORT).show();
                    et_nom.setText("");
                }else{
                    Toast.makeText(AffichageActivity.this,"Vous devez écrire votre nom",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void inscription(){
        SharedPreferences fichier=getSharedPreferences(CONST_NOM_FIC,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= fichier.edit();
        editor.putString("Etudiant",et_nom.getText().toString());
        editor.putString("Description",listeCours.get(positionCours).getDescription());
        editor.putString("CodeCours",listeCodesCours.get(positionCours));
        editor.putString("NomCours",listeCours.get(positionCours).getNomCours());
        editor.commit();
    }
}