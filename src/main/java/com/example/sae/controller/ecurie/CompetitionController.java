package com.example.sae.controller.ecurie;

import com.example.sae.models.Competition;
import com.example.sae.models.Poule;
import com.example.sae.repository.CompetitionRepository;
import com.example.sae.repository.PouleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ecurie/competition")
public class CompetitionController extends EcurieDashboard {

    private CompetitionRepository competitionRepository;
    private PouleRepository pouleRepository;

    public CompetitionController(CompetitionRepository competitionRepository, PouleRepository pouleRepository) {
        this.competitionRepository = competitionRepository;
        this.pouleRepository = pouleRepository;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable int id, Model model) {
        Competition c = competitionRepository.findById(id).orElse(null);
        assert c != null;


        Collection<Poule> poules = this.pouleRepository.findAllByCompetitionId(c.getId());

        Map<Object, List<Poule>> equipesBasedOnPoule = poules.stream().
                collect(Collectors.groupingBy(Poule::getPouleNum));

        model.addAttribute("competition", c);
        model.addAttribute("poules", equipesBasedOnPoule);

        return "ecurie/competition/details";
    }

}
