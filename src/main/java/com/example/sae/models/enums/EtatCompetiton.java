package com.example.sae.models.enums;

public enum EtatCompetiton {
    INSCRIPTION(1, "Inscription"),
    QUALIFICATION(2, "Qualification"),
    FINALE(3, "Finale"),
    FINI(4, "Compétition finie");

    private final int step;
    private final String niceName;

    EtatCompetiton(int step, String niceName) {
        this.step = step;
        this.niceName = niceName;
    }

    public String getNiceName() {
        return this.niceName;
    }

    @Override
    public String toString() {
        return this.niceName;
    }

    public int getStep() {
        return step;
    }
}
