package com.example.sae.models;

import com.example.sae.models.ref.JoueurRef;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Equipe {
    @Id
    @Column("equipe_id")
    private int id;
    private String nom;

    @Column("jeu_spe")
    private int jeuSpe;

    @Transient
    private Jeu jeuSpeModel;

    @Column("ecurie_id")
    private AggregateReference<Ecurie, Integer> ecurie;

    @MappedCollection(idColumn = "equipe_id")
    private Set<JoueurRef> joueurs = new HashSet<>();

    public Equipe() {
    }

    public Equipe(String nom, int jeuSpe) {
        this.nom = nom;
        this.jeuSpe = jeuSpe;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getJeuSpe() {
        return jeuSpe;
    }

    public void setJeuSpe(Integer jeuSpe) {
        this.jeuSpe = jeuSpe;
    }

    public void addJoueur(Joueur joueur) {
        this.joueurs.add(new JoueurRef(joueur.getId()));
    }

    public void addJoueur(JoueurRef joueurRef) {
        this.joueurs.add(joueurRef);
    }

    public Set<JoueurRef> getJoueursIds() {
        return joueurs;
    }

    public void removeJoueur(Integer joueurId) {
        this.joueurs = this.joueurs.stream().filter(joueurRef -> joueurRef.getJoueurId() != joueurId).collect(Collectors.toSet());
    }

    public Integer getNbJoueurs() {
        return this.joueurs.size();
    }

    public AggregateReference<Ecurie, Integer> getEcurie() {
        return ecurie;
    }

    public void setEcurie(AggregateReference<Ecurie, Integer> ecurie) {
        this.ecurie = ecurie;
    }

    @Override
    public String toString() {
        return "Equipe{" + "id=" + id + ", nom='" + nom + '\'' + ", jeuSpe='" + jeuSpe + '\'' + ", joueurs=" + joueurs + '}';
    }

    public Jeu getJeuSpeModel() {
        return jeuSpeModel;
    }

    public void setJeuSpeModel(Jeu jeuSpeModel) {
        this.jeuSpeModel = jeuSpeModel;
    }
}

