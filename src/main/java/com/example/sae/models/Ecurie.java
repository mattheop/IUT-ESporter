package com.example.sae.models;

import org.springframework.data.annotation.Id;

public class Ecurie {

    @Id
    private int id;
    private String nom_ecurie;

    public Ecurie() {
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom_ecurie;
    }
}
