package com.example.sae.exceptions.joueur;

public class JoueurShouldBeAttachedToEcurie extends RuntimeException {

    public JoueurShouldBeAttachedToEcurie() {
        super("Le joueur doit appartenir a une ecurie");
    }

    public JoueurShouldBeAttachedToEcurie(String message) {
        super(message);
    }
}
