package com.example.sae.exceptions.equipe;

public class EquipeNotFoundException extends RuntimeException {

    public EquipeNotFoundException() {
        super("Equipe not found");
    }

    public EquipeNotFoundException(String message) {
        super(message);
    }
}
