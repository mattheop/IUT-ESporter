package com.example.sae.controller.arbitre;

import com.example.sae.repository.CompetitionRepository;
import com.example.sae.repository.RencontreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/arbitre")
public class ArbitreDashboardController extends ArbitreDashboard {

    private CompetitionRepository competitionRepository;
    private RencontreRepository rencontreRepository;

    public ArbitreDashboardController(CompetitionRepository competitionRepository, RencontreRepository rencontreRepository) {
        this.competitionRepository = competitionRepository;
        this.rencontreRepository = rencontreRepository;
    }

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("nb_competition", this.competitionRepository.findAllInProgress().size());
        model.addAttribute("nb_match_a_arbitrer", this.rencontreRepository.countRencontreByCompetition());
        return "arbitre/home";
    }

}
