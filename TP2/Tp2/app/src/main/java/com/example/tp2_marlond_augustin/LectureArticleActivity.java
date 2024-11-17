package com.example.tp2_marlond_augustin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.tp2_marlond_augustin.databinding.ActivityLectureArticleBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LectureArticleActivity extends AppCompatActivity {
    ActivityLectureArticleBinding binding;
    FirebaseDatabase bd;
    DatabaseReference ref;
    FirebaseAuth bdAuth;
    private ArrayList<Article> listeArticles;
    int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLectureArticleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        listeArticles=new ArrayList<Article>();
        lectureDonnees();
        binding.lvArticles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                binding.lvArticles.setSelector(R.color.grey);
                Article articleCourant=listeArticles.get(i);
                String contenu=getString(R.string.soustitre)+": "+articleCourant.getSousTitre()
                        +"\n"+getString(R.string.categorie)+articleCourant.getCategorie()+"\n"+getString(R.string.messsage)+": "
                        +articleCourant.getMessage();
                position=i;
                Bundle bundle=new Bundle();
                bundle.putString("description",contenu);
                DescriptionArticleFragment monFragment=new DescriptionArticleFragment();
                monFragment.setArguments(bundle);
                FragmentManager fm=getSupportFragmentManager();
                fm.beginTransaction().replace(binding.flDescription.getId(), monFragment).commit();
            }
        });
        binding.btnEffacer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                effacerUnArticle();
            }
        });
        binding.btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position!=-1){
                    bdAuth=FirebaseAuth.getInstance();
                    bdAuth.getCurrentUser()
                            .reload()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    FirebaseUser usager=bdAuth.getCurrentUser();
                                    String uuid=usager.getUid().toString();
                                    Article articleCourant=listeArticles.get(position);
                                    if(articleCourant.getUuid().contains(uuid)) {
                                        Intent intent = new Intent(LectureArticleActivity.this, ModifierArticleActivity.class);
                                        Bundle bundle = new Bundle();
                                        intent.putExtra("Article", articleCourant);
                                        intent.putExtra("uuid",articleCourant.getUuid());
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(LectureArticleActivity.this,getString(R.string.modArticle),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(LectureArticleActivity.this,getString(R.string.article_erreur),Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    private void lectureDonnees(){
        //Notes de cours 27-BDFireBaseTempReel page 23 à 25
        bd=FirebaseDatabase.getInstance();
        ref=bd.getReference("Articles");
        final ArrayAdapter<Article> adapter=new ArrayAdapter<Article>(this,
                android.R.layout.simple_list_item_1,listeArticles);
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                listeArticles.add(snapshot.getValue(Article.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                listeArticles.remove(snapshot.getValue(Article.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.lvArticles.setAdapter(adapter);
    }
    private void effacerUnArticle(){
        /*
        Premièrement on vérifie si un article est sélectionné, sinon on n'efface pas l'article
        Deuxièmement on demande si l'usager veut vraiment effacer l'article selectionné
        Ensuite si l'usager a choisi un article on vérifie si l'article sélectionné a été créer par l'usager
         */
        bd=FirebaseDatabase.getInstance();
        ref=bd.getReference("Articles");
        //Log.d("Index article",""+position);
        if(position!=-1){
            bdAuth=FirebaseAuth.getInstance();
            bdAuth.getCurrentUser()
                    .reload()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUser usager=bdAuth.getCurrentUser();
                            String uuid=usager.getUid().toString();
                            Article articleCourant=listeArticles.get(position);
                            AlertDialog.Builder alerteDialogueBuilder=new AlertDialog.Builder(LectureArticleActivity.this);
                            alerteDialogueBuilder.setMessage(getString(R.string.dialog_message)+articleCourant.getTitre());

                            alerteDialogueBuilder.setPositiveButton(getString(R.string.oui), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if(articleCourant.getUuid().contains(uuid)){
                                        ref.child(articleCourant.getUuid().toString()).removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(LectureArticleActivity.this,getString(R.string.article_supprimer),
                                                                    Toast.LENGTH_SHORT).show();
                                                            listeArticles.clear();
                                                            lectureDonnees();
                                                        }else{
                                                            Toast.makeText(LectureArticleActivity.this,getString(R.string.article_supprimer_erreur),
                                                                    Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });
                                    }else{
                                        Toast.makeText(LectureArticleActivity.this,getString(R.string.modArticle),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            alerteDialogueBuilder.setNegativeButton(getString(R.string.non), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {}
                            });
                            AlertDialog alerteDialog=alerteDialogueBuilder.create();
                            alerteDialog.show();
                        }
                    });
        }else{
            Toast.makeText(LectureArticleActivity.this,getString(R.string.article_erreur),Toast.LENGTH_SHORT).show();
        }

    }
}