package com.example.sae.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.HashSet;
import java.util.Set;

public class Ecurie {

    @Id
    private int id;
    private String nom_ecurie;

    @MappedCollection(keyColumn = "ecurie_id", idColumn = "ecurie_id")
    private Set<Equipe> equipes = new HashSet<>();

    public Ecurie() {
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom_ecurie;
    }

    public void addEquipe(Equipe equipe) {
        this.equipes.add(equipe);
    }

    public Set<Equipe> getEquipes() {
        return equipes;
    }

    @Override
    public String toString() {
        return "Ecurie{" + "id=" + id + ", nom_ecurie='" + nom_ecurie + '\'' + ", equipes=" + equipes + '}';
    }
}
