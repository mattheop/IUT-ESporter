package com.example.sae.models;

import org.springframework.data.annotation.Id;

public class Tournois {

    @Id
    private Integer id;
    private String nom;
    private Integer cashpricepoints;
    private String etenduetournois;

    public Tournois() {
    }

    public Tournois(String nom, Integer cashPricePoints, String etendueTournois) {
        this.nom = nom;
        this.cashpricepoints = cashPricePoints;
        this.etenduetournois = etendueTournois;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getCashpricepoints() {
        return cashpricepoints;
    }

    public void setCashpricepoints(Integer cashpricepoints) {
        this.cashpricepoints = cashpricepoints;
    }

    public String getEtenduetournois() {
        return etenduetournois;
    }

    public void setEtenduetournois(String etenduetournois) {
        this.etenduetournois = etenduetournois;
    }

    @Override
    public String toString() {
        return "Tournois{" + "id=" + id + ", nom='" + nom + '\'' + ", cashPricePoints=" + cashpricepoints + ", etendueTournois='" + etenduetournois + '\'' + '}';
    }
}
