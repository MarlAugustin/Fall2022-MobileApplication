package com.example.laboratoire4_marlond_augustin;

public class Commande {
    int NoCommande,NoRepas;
    String NomClient,TelClient,Nom;
    double Prix;

    public Commande() {
    }

    public Commande(int noCommande, int noRepas, String nomClient, String telClient, String nom, double prix) {
        NoCommande = noCommande;
        NoRepas = noRepas;
        NomClient = nomClient;
        TelClient = telClient;
        Nom = nom;
        Prix = prix;
    }


    public int getNoCommande() {
        return NoCommande;
    }

    public void setNoCommande(int noCommande) {
        NoCommande = noCommande;
    }

    public int getNoRepas() {
        return NoRepas;
    }

    public void setNoRepas(int noRepas) {
        NoRepas = noRepas;
    }

    public String getNomClient() {
        return NomClient;
    }

    public void setNomClient(String nomClient) {
        NomClient = nomClient;
    }

    public String getTelClient() {
        return TelClient;
    }

    public void setTelClient(String telClient) {
        TelClient = telClient;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public double getPrix() {
        return Prix;
    }

    public void setPrix(double prix) {
        Prix = prix;
    }

}
