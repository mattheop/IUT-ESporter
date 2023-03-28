package com.example.sae.services;

import com.example.sae.models.enums.AppUserRole;
import com.example.sae.models.DashboardFunction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardFunctionService {

    private enum DashbboardIcon {
        LA_EYE("la-eye"),
        LA_MEDAL("la-medal"),
        LA_USER("la-user"),
        LA_USERS("la-users"),
        LA_ENVELOPE("la-envelope");

        private final String icon;

        DashbboardIcon(String icon) {
            this.icon = icon;
        }

        public String getIcon() {
            return icon;
        }
    }

    private List<DashboardFunction> getOrganisateurDashboardFunc() {
        return new ArrayList<>(List.of(
                new DashboardFunction("Vue générale", "/organisateur", DashbboardIcon.LA_EYE.getIcon()),
                new DashboardFunction("Tournois", "/organisateur/tournois", DashbboardIcon.LA_MEDAL.getIcon())
        ));
    }

    private List<DashboardFunction> getArbitreDashboardFunc() {
        return new ArrayList<>(List.of(
                new DashboardFunction("Compétitions", "/arbitre/competitions-a-arbitrer", DashbboardIcon.LA_MEDAL.getIcon())
        ));
    }


    private List<DashboardFunction> getEcurieDashboardFunc() {
        return new ArrayList<>(List.of(
                new DashboardFunction("Vue générale", "/ecurie", DashbboardIcon.LA_EYE.getIcon()),
                new DashboardFunction("Joueurs", "/ecurie/joueurs", DashbboardIcon.LA_USER.getIcon()),
                new DashboardFunction("Equipes", "/ecurie/equipes", DashbboardIcon.LA_USERS.getIcon()),
                new DashboardFunction("Mes inscriptions", "/ecurie/inscriptions", DashbboardIcon.LA_MEDAL.getIcon()),
                new DashboardFunction("Mes messages", "/ecurie/messages", DashbboardIcon.LA_ENVELOPE.getIcon())
        ));
    }

    public List<DashboardFunction> getFuncOnRole(AppUserRole role) {
        return switch (role) {
            case ROLE_ORGANISATEUR -> getOrganisateurDashboardFunc();
            case ROLE_ECURIE -> getEcurieDashboardFunc();
            case ROLE_ARBITRE -> getArbitreDashboardFunc();
            default -> new ArrayList<>();
        };
    }
}
