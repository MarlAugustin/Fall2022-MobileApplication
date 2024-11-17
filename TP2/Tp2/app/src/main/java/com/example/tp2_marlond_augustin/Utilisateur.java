package com.example.tp2_marlond_augustin;

import android.os.Parcel;
import android.os.Parcelable;

public class Utilisateur implements Parcelable {
    private String nom,courriel,motDePasse;

    public Utilisateur() {
    }

    public Utilisateur(String nom, String courriel) {
        this.nom = nom;
        this.courriel = courriel;
    }

    public Utilisateur(String nom, String courriel, String mdp) {
        this.nom = nom;
        this.courriel = courriel;
        this.motDePasse=mdp;
    }

    protected Utilisateur(Parcel in) {
        nom = in.readString();
        courriel = in.readString();
        motDePasse = in.readString();
    }

    public static final Creator<Utilisateur> CREATOR = new Creator<Utilisateur>() {
        @Override
        public Utilisateur createFromParcel(Parcel in) {
            return new Utilisateur(in);
        }

        @Override
        public Utilisateur[] newArray(int size) {
            return new Utilisateur[size];
        }
    };

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCourriel() {
        return courriel;
    }

    public void setCourriel(String courriel) {
        this.courriel = courriel;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nom);
        parcel.writeString(courriel);
        parcel.writeString(motDePasse);
    }
}
