package com.example.sae.models;

import org.springframework.data.annotation.Id;

public class Tournois {

    @Id
    private Integer id;
    private String nom;
    private Integer cashPricePoints;
    private String etendueTournois;

    public Tournois() {
    }

    public Tournois(String nom, Integer cashPricePoints, String etendueTournois) {
        this.nom = nom;
        this.cashPricePoints = cashPricePoints;
        this.etendueTournois = etendueTournois;
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

    public Integer getCashPricePoints() {
        return cashPricePoints;
    }

    public void setCashPricePoints(Integer cashPricePoints) {
        this.cashPricePoints = cashPricePoints;
    }

    public String getEtendueTournois() {
        return etendueTournois;
    }

    public void setEtendueTournois(String etendueTournois) {
        this.etendueTournois = etendueTournois;
    }

    @Override
    public String toString() {
        return "Tournois{" + "id=" + id + ", nom='" + nom + '\'' + ", cashPricePoints=" + cashPricePoints + ", etendueTournois='" + etendueTournois + '\'' + '}';
    }
}
