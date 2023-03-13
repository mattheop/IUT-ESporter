package com.example.sae.models.db;

import com.example.sae.models.ref.JoueurRef;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Equipe {
    @Id
    @Column("equipe_id")
    private int id;
    @NotBlank(message = "{feedback.emptyfield}")
    private String nom;

    @Column("logo_file_name")
    private String logoFileName;

    @Column("jeu_spe")
    @NotNull(message = "{feedback.emptyfield}")
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

    public String getLogoPath() {
        return "/uploads/equipes_logo/" + logoFileName;
    }

    public void setLogoFileName(String logoFileName) {
        this.logoFileName = logoFileName;
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

    public void setJoueursIds(Set<JoueurRef> joueurs){
        this.joueurs = joueurs;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipe equipe = (Equipe) o;
        return id == equipe.id && jeuSpe == equipe.jeuSpe && Objects.equals(nom, equipe.nom) && Objects.equals(logoFileName, equipe.logoFileName) && Objects.equals(jeuSpeModel, equipe.jeuSpeModel) && Objects.equals(ecurie.getId(), equipe.ecurie.getId()) && Objects.equals(joueurs, equipe.joueurs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, logoFileName, jeuSpe, jeuSpeModel, ecurie, joueurs);
    }
}

