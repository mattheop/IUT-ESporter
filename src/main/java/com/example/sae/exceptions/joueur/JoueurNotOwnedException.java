package com.example.sae.exceptions.joueur;

public class JoueurNotOwnedException extends RuntimeException {
    public JoueurNotOwnedException() {
        super("Ce joueur ne vous appartient pas");
    }

    public JoueurNotOwnedException(String message) {
        super(message);
    }
}
