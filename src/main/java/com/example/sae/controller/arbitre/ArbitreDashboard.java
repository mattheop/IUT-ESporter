package com.example.sae.controller.arbitre;

import com.example.sae.controller.ESporterDashboard;
import com.example.sae.models.db.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;

public class ArbitreDashboard extends ESporterDashboard {

    @ModelAttribute("appuser")
    public AppUser getAppUser(Authentication authentication) {
        assert authentication != null;
        return (AppUser) authentication.getPrincipal();
    }
}
