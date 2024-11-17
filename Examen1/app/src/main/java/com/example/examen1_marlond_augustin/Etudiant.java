package com.example.examen1_marlond_augustin;

import android.os.Parcel;
import android.os.Parcelable;

public class Etudiant implements Parcelable {
    int NoEtudiant;
    String NumeroDA,Prenom,Nom,DateNaissance,Programme;
    public Etudiant(){

    }
    public Etudiant(int noEtudiant, String numeroDA, String prenom, String nom, String dateNaissance, String programme) {
        NoEtudiant = noEtudiant;
        NumeroDA = numeroDA;
        Prenom = prenom;
        Nom = nom;
        DateNaissance = dateNaissance;
        Programme = programme;
    }

    protected Etudiant(Parcel in) {
        NoEtudiant = in.readInt();
        NumeroDA = in.readString();
        Prenom = in.readString();
        Nom = in.readString();
        DateNaissance = in.readString();
        Programme = in.readString();
    }

    public static final Creator<Etudiant> CREATOR = new Creator<Etudiant>() {
        @Override
        public Etudiant createFromParcel(Parcel in) {
            return new Etudiant(in);
        }

        @Override
        public Etudiant[] newArray(int size) {
            return new Etudiant[size];
        }
    };

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getDateNaissance() {
        return DateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        DateNaissance = dateNaissance;
    }

    public String getProgramme() {
        return Programme;
    }

    public void setProgramme(String programme) {
        Programme = programme;
    }

    public int getNoEtudiant() {
        return NoEtudiant;
    }

    public void setNoEtudiant(int noEtudiant) {
        NoEtudiant = noEtudiant;
    }

    public String getNumeroDA() {
        return NumeroDA;
    }

    public void setNumeroDA(String numeroDA) {
        NumeroDA = numeroDA;
    }

    public String getPrenom() {
        return Prenom;
    }

    public void setPrenom(String prenom) {
        Prenom = prenom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(NoEtudiant);
        parcel.writeString(NumeroDA);
        parcel.writeString(Prenom);
        parcel.writeString(Nom);
        parcel.writeString(DateNaissance);
        parcel.writeString(Programme);
    }
}
