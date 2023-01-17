package com.example.sae.models.db;

import com.example.sae.models.enums.EtatCompetiton;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Competition {

    @Id
    @Column("competition_id")
    private int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column("date_debut")
    private LocalDateTime dateFinInscription;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column("date_fin_inscription")
    private LocalDateTime dateDebutCompetition;

    @Column("jeu_id")
    private Jeu jeu;

    @Column("etat_competition")
    private EtatCompetiton etatCompetiton;

    @Column("np_participation")
    private int nbParticipation;

    @Column("is_full")
    private int isFull;

    @Column("tournois_id")
    private Tournois tournois;

    public Competition() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDateFinInscription() {
        return dateFinInscription;
    }

    public String getFormattedDateFinInscription() {
        return dateFinInscription.format(DateTimeFormatter.ofPattern("dd/dd/yyyy"));
    }

    public void setDateFinInscription(LocalDateTime dateFinInscription) {
        this.dateFinInscription = dateFinInscription;
    }

    public LocalDateTime getDateDebutCompetition() {
        return dateDebutCompetition;
    }

    public String getFormattedDateDebutCompetition() {
        return dateDebutCompetition.format(DateTimeFormatter.ofPattern("dd/dd/yyyy"));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Competition that = (Competition) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public boolean isFull() {
        return this.isFull == 1;
    }

    public void setFull(boolean full) {
        this.isFull = full ? 1 : 0;
    }

    public int getNbParticipation() {
        return nbParticipation;
    }

    public void setNbParticipation(int nbParticipation) {
        this.nbParticipation = nbParticipation;
    }

    public EtatCompetiton getEtatCompetiton() {
        return etatCompetiton;
    }

    public void setEtatCompetiton(EtatCompetiton etatCompetiton) {
        this.etatCompetiton = etatCompetiton;
    }
}
