package com.example.sae.controller.organisateur;

import com.example.sae.controller.ecurie.EcurieDashboard;
import com.example.sae.repository.CompetitionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("organisateur")
public class OrganisateurDashboardController extends OrganisateurDashboard {

    private CompetitionRepository competitionRepository;

    public OrganisateurDashboardController(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    @GetMapping()
    public String home() {


        System.out.println(competitionRepository.findAll());
        return "organisateur/home";
    }

}
