package com.example.sae.models.db;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

public class Inscription {

    @Id
    private int inscriptionId;

    @Column("equipe_id")
    private Equipe equipe;

    @Column("competition_id")
    private Competition competition;

    @Transient
    private Ecurie lazyEcurie;

    @Column("ecurie_points")
    private int ecuriePoints;

    @Column("rang_finale")
    private Long rangFinale;

    public int getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(int inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    @Override
    public String toString() {
        return "Inscription{" +
                "inscriptionId=" + inscriptionId +
                ", equipe=" + equipe +
                ", competition=" + competition +
                '}';
    }

    public Ecurie getLazyEcurie() {
        return lazyEcurie;
    }

    public void setLazyEcurie(Ecurie lazyEcurie) {
        this.lazyEcurie = lazyEcurie;
    }

    public int getEcuriePoints() {
        return ecuriePoints;
    }

    public Long getRangFinale() {
        return rangFinale;
    }

    public Long getNombrePointsGagne() {
        if (!competition.isFini())
            return null;

        if(rangFinale == null) return null;

        if (rangFinale == 1) {
            return (long) (competition.getTournois().getCashpricepoints() * 0.5);
        }

        return (long) (competition.getTournois().getCashpricepoints() * 0.25);
    }
}
