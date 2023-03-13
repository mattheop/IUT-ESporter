package com.example.sae.exceptions.equipe;

public class EquipeNotOwnedException extends RuntimeException {
    public EquipeNotOwnedException() {
        super("Cette equipe ne vous appartient pas");
    }

    public EquipeNotOwnedException(String message) {
        super(message);
    }
}
