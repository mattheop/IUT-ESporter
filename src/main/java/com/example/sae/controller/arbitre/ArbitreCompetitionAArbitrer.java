package com.example.sae.controller.arbitre;

import com.example.sae.models.db.Competition;
import com.example.sae.repository.CompetitionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/arbitre/competitions-a-arbitrer")
public class ArbitreCompetitionAArbitrer extends ArbitreDashboard {

    private CompetitionRepository competitionRepository;

    public ArbitreCompetitionAArbitrer(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
    }

    @GetMapping("")
    public String showInscriptions(Model model) {
        List<Competition> competitionAArbitrer = competitionRepository.findAllInProgress();

        model.addAttribute("competitions", competitionAArbitrer);

        return "arbitre/competitionAArbitrer";
    }

}
