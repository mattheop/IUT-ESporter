package com.example.sae.controller.organisateur;

import com.example.sae.controller.ESporterDashboard;
import com.example.sae.models.AppUser;
import com.example.sae.models.AppUserRole;
import com.example.sae.services.DashboardFunctionService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

public class OrganisateurDashboard extends ESporterDashboard {

    @ModelAttribute("appuser")
    public AppUser getAppUser(Authentication authentication) {
        assert authentication != null;
        AppUser appUser = (AppUser) authentication.getPrincipal();

        return appUser;
    }
}
