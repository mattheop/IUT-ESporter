package com.example.sae.models.ref;

import org.springframework.data.relational.core.mapping.Table;

@Table("joueur_equipe")
public class JoueurRef {
    int joueurId;

    public JoueurRef(int joueurId) {
        this.joueurId = joueurId;
    }
}
