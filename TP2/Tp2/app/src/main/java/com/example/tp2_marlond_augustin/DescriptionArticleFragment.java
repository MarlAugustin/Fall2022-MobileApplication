package com.example.tp2_marlond_augustin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DescriptionArticleFragment extends Fragment {



    public DescriptionArticleFragment() {
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
        View vue=inflater.inflate(R.layout.fragment_description_article,
                container, false);
        TextView tv_description=vue.findViewById(R.id.tvDescription);
        Bundle bundle=getArguments();
        if(bundle!=null)
            if(bundle.getString("description")!=null){
                String contenu=bundle.getString("description");
                tv_description.setText(contenu);
            }
        return vue;
    }
}