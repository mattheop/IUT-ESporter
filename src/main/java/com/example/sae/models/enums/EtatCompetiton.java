package com.example.sae.models.enums;

public enum EtatCompetiton {
    INSCRIPTION("Inscription"),
    QUALIFICATION("Qualification"),
    FINALE("Finale"),
    FINI("Compétition finie");

    private String niceName;

    EtatCompetiton(String niceName) {
        this.niceName = niceName;
    }

    public String getNiceName() {
        return this.niceName;
    }

    @Override
    public String toString() {
        return this.niceName;
    }
}
