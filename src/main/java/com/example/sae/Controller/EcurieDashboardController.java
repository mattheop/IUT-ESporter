package com.example.sae.Controller;

import com.example.sae.models.AppUser;
import com.example.sae.repository.EcurieRepository;
import com.example.sae.repository.JoueurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class EcurieDashboardController {

    final
    EcurieRepository repository;

    public EcurieDashboardController(EcurieRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public String home(Authentication authentication) {

    /*    AppUser appUser = (AppUser) authentication.getPrincipal();
        authentication.getAuthorities();*/


        return this.repository.getJoueurs(1).toString();
    }

    @GetMapping("/ecurie")
    public String ecurieDash() {
        return "ok";
    }
}
