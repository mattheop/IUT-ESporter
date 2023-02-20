package com.example.sae.controller.arbitre.RESTBody;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ArbitreScoreRESTBody {

    @JsonProperty("rencontreId")
    private Integer rencontreID;

    @JsonProperty("competitionId")
    private Integer competitionID;
    @JsonProperty("equipe1")
    private Integer equipe1;
    @JsonProperty("equipe2")
    private Integer equipe2;

    public ArbitreScoreRESTBody() {
    }

    public ArbitreScoreRESTBody(Integer competitionID, Integer rencontreID, Integer equipe1, Integer equipe2) {
        this.competitionID = competitionID;
        this.rencontreID = rencontreID;
        this.equipe1 = equipe1;
        this.equipe2 = equipe2;
    }

    public Integer getRencontreID() {
        return rencontreID;
    }

    public void setRencontreID(Integer rencontreID) {
        this.rencontreID = rencontreID;
    }

    public Integer getEquipe1() {
        return equipe1;
    }

    public void setEquipe1(Integer equipe1) {
        this.equipe1 = equipe1;
    }

    public Integer getEquipe2() {
        return equipe2;
    }

    public void setEquipe2(Integer equipe2) {
        this.equipe2 = equipe2;
    }

    public Integer getCompetitionID() {
        return competitionID;
    }

    public void setCompetitionID(Integer competitionID) {
        this.competitionID = competitionID;
    }
}
