package com.example.tp2_marlond_augustin;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {
    String uuid;
    String titre;
    String sousTitre;
    String categorie;
    String message;

    public Article() {
    }

    public Article(String uuid, String titre, String sousTitre, String categorie, String message) {
        this.uuid = uuid;
        this.titre = titre;
        this.sousTitre = sousTitre;
        this.categorie = categorie;
        this.message = message;
    }

    protected Article(Parcel in) {
        uuid = in.readString();
        titre = in.readString();
        sousTitre = in.readString();
        categorie = in.readString();
        message = in.readString();
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSousTitre() {
        return sousTitre;
    }

    public void setSousTitre(String sousTitre) {
        this.sousTitre = sousTitre;
    }
    @Override
    public String toString(){
        return titre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uuid);
        parcel.writeString(titre);
        parcel.writeString(sousTitre);
        parcel.writeString(categorie);
        parcel.writeString(message);
    }
}
