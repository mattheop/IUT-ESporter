package com.example.sae.exceptions.equipe;

public class EquipeUploadLogoException extends RuntimeException {

    public EquipeUploadLogoException() {
        super("Fatal error when uploading logo. Please check permission.");
    }

    public EquipeUploadLogoException(String message) {
        super(message);
    }
}
