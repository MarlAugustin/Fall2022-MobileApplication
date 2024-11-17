package com.example.tp2_marlond_augustin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.tp2_marlond_augustin.databinding.ActivityModifierArticleBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ModifierArticleActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    FirebaseAuth bdAuth;
    ActivityModifierArticleBinding binding;
    String categorieChoisie="";
    private String uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityModifierArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        List<String> arrayList=new ArrayList<String>();
        Intent intent=getIntent();
        Article article=intent.getParcelableExtra("Article");
        uuid=intent.getStringExtra("uuid");
        arrayList.add("Sport");
        arrayList.add("Actualité");
        arrayList.add("Entertainment");
        arrayList.add("Crypto");
        arrayList.add("Technology");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getLayoutInflater().getContext(),android.R.layout.simple_list_item_1,arrayList);
        binding.lvCategorie.setAdapter(adapter);
        int position=arrayList.indexOf(article.getCategorie());
        categorieChoisie=article.getCategorie();
        Log.d("Position",""+position);
        binding.lvCategorie.post(new Runnable() {
            @Override
            public void run() {
                binding.lvCategorie.clearFocus();
                binding.lvCategorie.requestFocusFromTouch();
                //binding.lvCategorie.setItemChecked(position,true);
                binding.lvCategorie.setSelection(position);
                binding.lvCategorie.setSelector(R.color.grey);

            }
        });
        binding.lvCategorie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                binding.lvCategorie.setSelector(R.color.grey);
                categorieChoisie=arrayList.get(i);
            }
        });
        binding.tietMessage.setText(article.getMessage());
        binding.tietSousTitre.setText(article.getSousTitre());
        binding.tietTitre.setText(article.getTitre());
        binding.btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(champsValide()){
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
                                    Article article=new Article(uuid,titre,sousTitre,categorieChoisie,message);
                                    ref=database.getReference("Articles");
                                    ref.child(uuid).setValue(article).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ModifierArticleActivity.this,getString(R.string.article_update),Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(ModifierArticleActivity.this,GestionActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                            });
                }else{
                    if(categorieChoisie==""){
                        Toast.makeText(ModifierArticleActivity.this,getString(R.string.categorie_erreur),Toast.LENGTH_SHORT).show();

                    }
                    if(binding.tietTitre.getText().length()==0||binding.tietSousTitre.getText().toString().length()==0){
                        Toast.makeText(ModifierArticleActivity.this,getString(R.string.titre_soustitre_erreur),Toast.LENGTH_SHORT).show();
                    }
                    if(binding.tietMessage.getText().length()<10){
                        Toast.makeText(ModifierArticleActivity.this,getString(R.string.message_erreur),Toast.LENGTH_SHORT).show();

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
}