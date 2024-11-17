package com.example.examen2_marlond_augustin_final;

import android.os.Parcel;
import android.os.Parcelable;

public class cours implements Parcelable {
    String codeCours,nomCours,description;

    public cours() {
    }

    public cours(String codeCours, String nomCours, String description) {
        this.codeCours = codeCours;
        this.nomCours = nomCours;
        this.description = description;
    }

    protected cours(Parcel in) {
        codeCours = in.readString();
        nomCours = in.readString();
        description = in.readString();
    }

    public static final Creator<cours> CREATOR = new Creator<cours>() {
        @Override
        public cours createFromParcel(Parcel in) {
            return new cours(in);
        }

        @Override
        public cours[] newArray(int size) {
            return new cours[size];
        }
    };

    public String getCodeCours() {
        return codeCours;
    }

    public void setCodeCours(String codeCours) {
        this.codeCours = codeCours;
    }

    public String getNomCours() {
        return nomCours;
    }

    public void setNomCours(String nomCours) {
        this.nomCours = nomCours;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(codeCours);
        parcel.writeString(nomCours);
        parcel.writeString(description);
    }
}
