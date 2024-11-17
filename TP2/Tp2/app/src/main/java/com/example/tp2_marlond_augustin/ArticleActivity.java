package com.example.tp2_marlond_augustin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tp2_marlond_augustin.databinding.ActivityArticleBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    FirebaseAuth bdAuth;
    FirebaseDatabase bd;
    ActivityArticleBinding binding;
    String categorieChoisie="";
    private int nbApparition=0;
    private ArrayList<String> listeUuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<String> arrayList=new ArrayList<String>();
        arrayList.add("Sport");
        arrayList.add("Actualité");
        arrayList.add("Entertainment");
        arrayList.add("Crypto");
        arrayList.add("Technology");
        listeUuid=new ArrayList<String>();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getLayoutInflater().getContext(),android.R.layout.simple_list_item_1,arrayList);
        binding.lvCategorie.setAdapter(adapter);
        binding.lvCategorie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                categorieChoisie=arrayList.get(i);
                binding.lvCategorie.setSelector(R.color.grey);
            }
        });
        binding.btnEnregistrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(champsValide()){
                    enregistrementDonnees();

                }else{
                    if(categorieChoisie==""){
                        Toast.makeText(ArticleActivity.this,getString(R.string.categorie_erreur),Toast.LENGTH_SHORT).show();

                    }
                    if(binding.tietTitre.getText().length()==0||binding.tietSousTitre.getText().toString().length()==0){
                        Toast.makeText(ArticleActivity.this,getString(R.string.titre_soustitre_erreur),Toast.LENGTH_SHORT).show();
                    }
                    if(binding.tietMessage.getText().length()<10){
                        Toast.makeText(ArticleActivity.this,getString(R.string.message_erreur),Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }
    boolean champsValide(){
        boolean valide=false;
        if(binding.tietTitre.getText().toString().length()>0&&binding.tietSousTitre.getText().toString().length()>0
                &&binding.tietMessage.getText().toString().length()>=10&&categorieChoisie!=""){
            valide=true;
        }
        return valide;
    }
    private void enregistrementDonnees(){
        //J'ai créer ça dans le but de pouvoir ajouter des articles qui contiennent
        //https://stackoverflow.com/questions/38965731/how-to-get-all-childs-data-in-firebase-database
        bd=FirebaseDatabase.getInstance();
        ref=bd.getReference().child("Articles");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                lectureDonnees((Map<String,Object>) snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
     void lectureDonnees(Map<String,Object> users){
        int i=0;
        for (Map.Entry<String, Object> entry : users.entrySet()){
            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get uuid field and append to list
            listeUuid.add((String) singleUser.get("uuid"));
            //Log.d("listeUUid",""+listeUuid.get(i).toString());
            i+=1;
        }
         bdAuth=FirebaseAuth.getInstance();
         String titre=binding.tietTitre.getText().toString();
         String sousTitre=binding.tietSousTitre.getText().toString();
         String message=binding.tietMessage.getText().toString();
         bdAuth.getCurrentUser()
                 .reload()
                 .addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void unused) {
                         FirebaseUser usager=bdAuth.getCurrentUser();
                         for(int i=0;i<listeUuid.size();i++){
                             if(listeUuid.get(i).toString().contains(usager.getUid().toString())){
                                 nbApparition+=1;
                             }
                         }
                         //Log.d("Apparitions",""+nbApparition);
                         String uuid;
                         if(nbApparition==0){
                             uuid=usager.getUid().toString();

                         }else{
                             uuid=usager.getUid().toString()+"_"+nbApparition;
                         }
                         Article article=new Article(uuid,titre,sousTitre,categorieChoisie,message);
                         ref=database.getReference("Articles");
                         ref.child(uuid).setValue(article).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 Toast.makeText(ArticleActivity.this,getString(R.string.article_creer),Toast.LENGTH_SHORT).show();
                                 Intent intent=new Intent(ArticleActivity.this,GestionActivity.class);
                                 startActivity(intent);
                                 finish();
                             }
                         });
                     }
                 });
    }
}