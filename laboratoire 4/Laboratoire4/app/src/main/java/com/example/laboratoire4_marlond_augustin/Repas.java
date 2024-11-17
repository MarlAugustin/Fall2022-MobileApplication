package com.example.laboratoire4_marlond_augustin;

import android.os.Parcel;
import android.os.Parcelable;

public class Repas implements Parcelable {
    int NoRepas;
    String Nom, Description, Categorie;
    Double Prix;

    public Repas() {
    }

    public Repas(int noRepas, String nom, String description, String categorie, Double prix) {
        NoRepas = noRepas;
        Nom = nom;
        Description = description;
        Categorie = categorie;
        Prix = prix;
    }

    protected Repas(Parcel in) {
        NoRepas = in.readInt();
        Nom = in.readString();
        Description = in.readString();
        Categorie = in.readString();
        if (in.readByte() == 0) {
            Prix = null;
        } else {
            Prix = in.readDouble();
        }
    }

    public static final Creator<Repas> CREATOR = new Creator<Repas>() {
        @Override
        public Repas createFromParcel(Parcel in) {
            return new Repas(in);
        }

        @Override
        public Repas[] newArray(int size) {
            return new Repas[size];
        }
    };

    public int getNoRepas() {
        return NoRepas;
    }

    public void setNoRepas(int noRepas) {
        NoRepas = noRepas;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }

    public Double getPrix() {
        return Prix;
    }

    public void setPrix(Double prix) {
        Prix = prix;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(NoRepas);
        parcel.writeString(Nom);
        parcel.writeString(Description);
        parcel.writeString(Categorie);
        if (Prix == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(Prix);
        }
    }
}
