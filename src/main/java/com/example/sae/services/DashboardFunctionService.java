package com.example.sae.services;

import com.example.sae.models.AppUser;
import com.example.sae.models.AppUserRole;
import com.example.sae.models.DashboardFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardFunctionService {

    private List<DashboardFunction> getOrganisateurDashboardFunc() {
        return new ArrayList<>(List.of(
                new DashboardFunction("Vue générale", "/organisateur", "la-eye"),
                new DashboardFunction("Tournois", "/organisateur/tournois", "la-medal")
        ));
    }

    private List<DashboardFunction> getEcurieDashboardFunc() {
        return new ArrayList<>(List.of(
                new DashboardFunction("Vue générale", "/ecurie", "la-eye"),
                new DashboardFunction("Joueurs", "/ecurie/joueurs", "la-user"),
                new DashboardFunction("Equipes", "/ecurie/equipes", "la-users"),
                new DashboardFunction("Mes inscriptions", "/ecurie/inscriptions", "la-medal"),
                new DashboardFunction("Planning", "/ecurie/planning", "la-calendar")
        ));
    }

    public List<DashboardFunction> getFuncOnRole(AppUserRole role) {
        return switch (role) {
            case ROLE_ORGANISATEUR -> getOrganisateurDashboardFunc();
            case ROLE_ECURIE -> getEcurieDashboardFunc();
            default -> new ArrayList<>();
        };
    }
}
