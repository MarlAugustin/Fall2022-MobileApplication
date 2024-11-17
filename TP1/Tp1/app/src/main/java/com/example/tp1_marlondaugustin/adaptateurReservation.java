package com.example.tp1_marlondaugustin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adaptateurReservation extends BaseAdapter {
    private Context dContext;
    private List<reservation> reservationList=  new ArrayList<>();
    private TextView tv_nomClient,tv_infoReservation;
    private ImageView iv_tableReservation;

    public adaptateurReservation(Context dContext, List<reservation> reservationList) {
        this.dContext = dContext;
        this.reservationList = reservationList;
    }

    @Override
    public int getCount() {
        return reservationList.size();
    }

    @Override
    public reservation getItem(int position) {
        return reservationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(dContext).inflate(R.layout.activity_list_view_row_reservation,viewGroup,false);
        tv_nomClient=view.findViewById(R.id.tv_nomClient);
        tv_nomClient.setText(this.getItem(position).getNomPersonne());

        tv_infoReservation=view.findViewById(R.id.tv_infoReservation);
        tv_infoReservation.setText(this.getItem(position).getNbPlace()+" "+dContext.getResources().getString(R.string.place).toString()
                +" - "+this.getItem(position).getBlocReservationDebut()+" - "+this.getItem(position).getBlocReservationFin()
        );
        iv_tableReservation=view.findViewById(R.id.iv_tableReservation);
        iv_tableReservation.setImageResource(R.drawable.affichage_reservation);

        return view;


    }
}
