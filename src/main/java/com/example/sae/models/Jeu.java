package com.example.sae.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

public class Jeu{

    @Id
    @Column("jeu_id")
    private Integer id;
    private String nom;

    @Column("nb_joueur")
    private Integer nbJoueurParEquipe;

    public Jeu(String nom, Integer nbJoueurParEquipe) {
        this.nom = nom;
        this.nbJoueurParEquipe = nbJoueurParEquipe;
    }

    public Jeu() {
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNbJoueurParEquipe(Integer nbJoueurParEquipe) {
        this.nbJoueurParEquipe = nbJoueurParEquipe;
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Integer getNbJoueurParEquipe() {
        return nbJoueurParEquipe;
    }

    @Override
    public String toString() {
        return "Jeu{" + "id=" + id + ", nom='" + nom + '\'' + ", nbJoueurParEquipe=" + nbJoueurParEquipe + '}';
    }
}
