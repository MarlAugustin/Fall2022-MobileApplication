package com.example.laboratoire4_marlond_augustin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
// import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        View vue=inflater.inflate(R.layout.fragment_description,
                container,false);
        TextView tv_description=vue.findViewById(R.id.tv_description);
        TextView tv_Categorie=vue.findViewById(R.id.tv_categorie);
        Bundle bundle=getArguments();
        if(bundle!=null)
            if(bundle.getParcelable("repasChoisi")!=null){
                Repas repasChoisi=bundle.getParcelable("repasChoisi");
                tv_Categorie.setText(repasChoisi.getCategorie().toString());
                tv_description.setText(repasChoisi.getDescription().toString());
            }
        return vue;
    }
}