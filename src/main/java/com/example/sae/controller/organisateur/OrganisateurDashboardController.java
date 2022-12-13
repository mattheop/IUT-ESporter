package com.example.sae.controller.organisateur;

import com.example.sae.controller.ecurie.EcurieDashboard;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("organisateur")
public class OrganisateurDashboardController extends OrganisateurDashboard {

    @GetMapping()
    public String home() {
        return "organisateur/home";
    }




}
