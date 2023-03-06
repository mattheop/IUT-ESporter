package com.example.sae.exceptions.joueur;

public class JoueurNotFoundException extends RuntimeException {

    public JoueurNotFoundException() {
        super("The user does not exist");
    }

    public JoueurNotFoundException(String message) {
        super(message);
    }
}
