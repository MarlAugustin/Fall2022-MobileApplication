package com.example.tp1_marlondaugustin;

import android.os.Parcel;
import android.os.Parcelable;

public class restaurant implements Parcelable {
    int noRestaurant;
    int nbPlacesMax;
    int nbPlacesRestantes;
    String nomRestaurant;

    public restaurant(int noRestaurant, int nbPlacesMax, int nbPlacesRestantes, String nomRestaurant) {
        this.noRestaurant = noRestaurant;
        this.nbPlacesMax = nbPlacesMax;
        this.nbPlacesRestantes = nbPlacesRestantes;
        this.nomRestaurant = nomRestaurant;
    }

    protected restaurant(Parcel in) {
        noRestaurant = in.readInt();
        nbPlacesMax = in.readInt();
        nbPlacesRestantes = in.readInt();
        nomRestaurant = in.readString();
    }

    public static final Creator<restaurant> CREATOR = new Creator<restaurant>() {
        @Override
        public restaurant createFromParcel(Parcel in) {
            return new restaurant(in);
        }

        @Override
        public restaurant[] newArray(int size) {
            return new restaurant[size];
        }
    };

    public int getNoRestaurant() {
        return noRestaurant;
    }

    public void setNoRestaurant(int noRestaurant) {
        this.noRestaurant = noRestaurant;
    }

    public int getNbPlacesMax() {
        return nbPlacesMax;
    }

    public void setNbPlacesMax(int nbPlacesMax) {
        this.nbPlacesMax = nbPlacesMax;
    }

    public int getNbPlacesRestantes() {
        return nbPlacesRestantes;
    }

    public void setNbPlacesRestantes(int nbPlacesRestantes) {
        this.nbPlacesRestantes = nbPlacesRestantes;
    }

    public String getNomRestaurant() {
        return nomRestaurant;
    }

    public void setNomRestaurant(String nomRestaurant) {
        this.nomRestaurant = nomRestaurant;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(noRestaurant);
        parcel.writeInt(nbPlacesMax);
        parcel.writeInt(nbPlacesRestantes);
        parcel.writeString(nomRestaurant);
    }
}
