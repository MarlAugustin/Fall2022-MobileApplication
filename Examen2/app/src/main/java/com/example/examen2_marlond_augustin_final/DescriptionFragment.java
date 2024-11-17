package com.example.examen2_marlond_augustin_final;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class DescriptionFragment extends Fragment {

    public DescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vue=inflater.inflate(R.layout.fragment_description, container, false);
        TextView tv_titre=vue.findViewById(R.id.tv_titre);
        TextView tv_description=vue.findViewById(R.id.tv_description);
        Bundle bundle=getArguments();
        if(bundle!=null)
            if(bundle.getParcelable("cours")!=null){
                cours coursActuelle=bundle.getParcelable("cours");
                String titre=coursActuelle.getNomCours();
                String description=coursActuelle.getDescription();
                tv_titre.setText(titre);
                tv_description.setText(description);

            }
        return vue;    }
}