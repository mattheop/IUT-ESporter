package com.example.sae.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Competition {

    @Id
    @Column("competition_id")
    private int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column("date_debut")
    private LocalDateTime dateFinInscription;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column("date_fin_inscription")
    private LocalDateTime dateDebutCompetition;

    @Column("jeu_id")
    private Jeu jeu;

    @Column("tournois_id")
    private Tournois tournois;

    public Competition() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateFinInscription() {
        return dateFinInscription;
    }

    public void setDateFinInscription(LocalDateTime dateFinInscription) {
        this.dateFinInscription = dateFinInscription;
    }

    public LocalDateTime getDateDebutCompetition() {
        return dateDebutCompetition;
    }

    public void setDateDebutCompetition(LocalDateTime dateDebutCompetition) {
        this.dateDebutCompetition = dateDebutCompetition;
    }

    public Jeu getJeu() {
        return jeu;
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    public Tournois getTournois() {
        return tournois;
    }

    public void setTournois(Tournois tournois) {
        this.tournois = tournois;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", dateFinInscription=" + dateFinInscription +
                ", dateDebutCompetition=" + dateDebutCompetition +
                ", jeu=" + jeu +
                ", tournois=" + tournois +
                '}';
    }
}
