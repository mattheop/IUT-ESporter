package com.example.sae.models;

import com.example.sae.models.ref.JoueurRef;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.util.HashSet;
import java.util.Set;

public class Equipe {
    @Id
    private int id;
    private String nom;

    @Column("jeu_spe")
    private String jeuSpe;

    @Column("ecurie_id")
    private AggregateReference<Ecurie, Integer> ecurie;

    @MappedCollection(idColumn = "equipe_id")
    private Set<JoueurRef> joueurs = new HashSet<>();

    public Equipe() {
    }

    public Equipe(String nom, String jeuSpe) {
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

    public String getJeuSpe() {
        return jeuSpe;
    }

    public void setJeuSpe(String jeuSpe) {
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
}
