package com.example.sae.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("inscription_poule")
public class Poule {

    @Id
    @Column("inscription_id")
    private int inscriptionId;

    @Column("poule_num")
    private int pouleNum;

    @Column("ecurie_points")
    private int ecuriePoints;

    private Equipe equipe;

    public Poule() {
    }

    public int getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(int inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public int getPouleNum() {
        return pouleNum;
    }

    public void setPouleNum(int pouleNum) {
        this.pouleNum = pouleNum;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public int getEcuriePoints() {
        return ecuriePoints;
    }

    public void setEcuriePoints(int ecuriePoints) {
        this.ecuriePoints = ecuriePoints;
    }
}
