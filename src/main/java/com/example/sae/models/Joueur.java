package com.example.sae.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Joueur {

    @Id
    private Integer id;
    private String prenom;
    private String nom;
    private String pseudo;
    private String nationnalite;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate entree_pro;

    private AggregateReference<Ecurie, Integer> ecurie;

    public Joueur() {
    }

    public Joueur(String prenom, String nom, String pseudo, String nationnalite) {
        this(prenom, nom, pseudo, nationnalite, LocalDate.now());
    }

    public Joueur(String prenom, String nom, String pseudo, String nationnalite, LocalDate entree_pro) {
        this.prenom = prenom;
        this.nom = nom;
        this.pseudo = pseudo;
        this.nationnalite = nationnalite;
        this.entree_pro = entree_pro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNationnalite() {
        return nationnalite;
    }

    public void setNationnalite(String nationnalite) {
        this.nationnalite = nationnalite;
    }

    public LocalDate getEntree_pro() {
        return entree_pro;
    }

    public void setEntree_pro(LocalDate entree_pro) {
        this.entree_pro = entree_pro;
    }

    public void setEcurie(AggregateReference<Ecurie, Integer> ecurie) {
        this.ecurie = ecurie;
    }

    public String toString() {
        return "Player{" +
                "id=" + id +
                ", prenom='" + prenom + '\'' +
                ", nom='" + nom + '\'' +
                ", pseudo='" + pseudo + '\'' +
                ", nationnalite='" + nationnalite + '\'' +
                ", entree_pro=" + entree_pro +
                '}';
    }
}
