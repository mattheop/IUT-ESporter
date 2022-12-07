package com.example.sae.controller.ecurie;

import com.example.sae.models.AppUser;
import com.example.sae.models.Ecurie;
import com.example.sae.repository.EcurieRepository;
import com.example.sae.repository.JoueurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ModelAttribute;

public class EcurieDashboard {

    @Autowired
    private EcurieRepository ecurieRepository;

    @ModelAttribute("appuser")
    public AppUser getAppUser(Authentication authentication) {
        assert authentication != null;
        AppUser appUser = (AppUser) authentication.getPrincipal();

        return appUser;
    }

    @ModelAttribute("ecurie")
    public Ecurie getManagedEcurie(@ModelAttribute("appuser") AppUser appUser){
        Ecurie managedEcurie = this.ecurieRepository.findById(appUser.getManagedEcurieId()).orElse(null);
        assert managedEcurie != null;

        return managedEcurie;
    }

}
