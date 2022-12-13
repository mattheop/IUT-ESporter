package com.example.sae.models;

public class DashboardFunction {
    private final String nom;
    private final String route;
    private final String icon;

    public DashboardFunction(String nom, String route, String icon) {
        this.nom = nom;
        this.route = route;
        this.icon = icon;
    }

    public String getNom() {
        return nom;
    }

    public String getRoute() {
        return route;
    }

    public String getIcon() {
        return icon;
    }
}
