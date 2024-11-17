package com.example.laboratoire4_marlond_augustin;

//import android.app.Fragment;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;


public class ListeFragment extends Fragment {

    List<String> listeNomPrixRepas=new ArrayList<String>();
    List<Repas> listeRepas=new ArrayList<Repas>();
    String nomFichier="Repas.json";
    public ListeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vue=inflater.inflate(R.layout.fragment_list,container,false);
        ListView lv_repas=vue.findViewById(R.id.lv_repas);
        String dataFichierJson=ouvrirFichierJson(container.getContext(),nomFichier);
        Gson gson=new Gson();
        Type listeEtudiantType=new TypeToken<List<Repas>>() { }.getType();
        listeRepas=gson.fromJson(dataFichierJson,listeEtudiantType);
        for(Repas repas:listeRepas){
            //On ajoute dans cette liste le nom et le prix de chacun des repas
            listeNomPrixRepas.add(repas.getNom().toUpperCase(Locale.ROOT)+"               "+repas.getPrix()+"$");
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,listeNomPrixRepas);
        lv_repas.setAdapter(adapter);
        lv_repas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //On envoie
                Repas repasChoisi=listeRepas.get(i);
                DescriptionFragment descriptionFragment=new DescriptionFragment();
                Bundle bnd = new Bundle();
                bnd.putParcelable("repasChoisi", repasChoisi);
                //bnd.putString("nomRepas",listeRepas.get(i).getNom());
                descriptionFragment.setArguments(bnd);
                FragmentManager fm=getFragmentManager();
                lv_repas.setSelector(android.R.color.holo_blue_dark);
                fm.beginTransaction().replace(R.id.fl_description,descriptionFragment).commit();
            }
        });

        return vue;
    }
    static String ouvrirFichierJson(Context contexte, String nomFichier){
        //On ouvre et on affiche les données dans le textView
        String stringJson;
        try{
            InputStream in=contexte.getAssets().open(nomFichier);

            int taille=in.available();
            byte[]tampon=new byte[taille];
            in.read(tampon);
            in.close();

            stringJson=new String(tampon,"UTF-8");

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return stringJson;
    }
}