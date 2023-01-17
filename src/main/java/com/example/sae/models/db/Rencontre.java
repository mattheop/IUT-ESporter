package com.example.sae.models.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rencontre {

    @Id
    @Column("rencontre_id")
    private int id;

    @Column("competition_id")
    private int competition;

    @Column("equipe1")
    private Equipe equipe1;

    @Column("equipe2")
    private Equipe equipe2;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column("date_rencontre")
    private LocalDateTime dateRencontre;

    @Column("score_equipe1")
    private Long scoreEquipe1;

    @Column("score_equipe2")
    private Long scoreEquipe2;

    @Column("winner")
    private Long winner;

    @Column("poule_num")
    private int pouleNumero;

    public Rencontre() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompetition(int competition) {
        this.competition = competition;
    }

    public Equipe getEquipe1() {
        return equipe1;
    }

    public void setEquipe1(Equipe equipe1) {
        this.equipe1 = equipe1;
    }

    public Equipe getEquipe2() {
        return equipe2;
    }

    public void setEquipe2(Equipe equipe2) {
        this.equipe2 = equipe2;
    }

    public LocalDateTime getDateRencontre() {
        return dateRencontre;
    }

    public String getFormattedDateRencontre() {
        return dateRencontre.format(DateTimeFormatter.ofPattern("dd/dd/yyyy à hh:mm"));
    }

    public void setDateRencontre(LocalDateTime dateRencontre) {
        this.dateRencontre = dateRencontre;
    }

    public Long getScoreEquipe1() {
        return scoreEquipe1;
    }

    public void setScoreEquipe1(Long scoreEquipe1) {
        this.scoreEquipe1 = scoreEquipe1;
    }

    public Long getScoreEquipe2() {
        return scoreEquipe2;
    }

    public void setScoreEquipe2(Long scoreEquipe2) {
        this.scoreEquipe2 = scoreEquipe2;
    }

    public Long getWinner() {
        return winner;
    }

    public void setWinner(Long winner) {
        this.winner = winner;
    }

    public int getPouleNumero() {
        return pouleNumero;
    }

    public void setPouleNumero(int pouleNumero) {
        this.pouleNumero = pouleNumero;
    }
}
