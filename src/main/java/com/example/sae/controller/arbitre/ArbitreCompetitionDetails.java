package com.example.sae.controller.arbitre;

import com.example.sae.models.db.*;
import com.example.sae.repository.CompetitionRepository;
import com.example.sae.repository.PouleRepository;
import com.example.sae.repository.RencontreRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/arbitre/competition/{id}")
public class ArbitreCompetitionDetails extends ArbitreDashboard {

    private CompetitionRepository competitionRepository;
    private PouleRepository pouleRepository;
    private RencontreRepository rencontreRepository;

    public ArbitreCompetitionDetails(CompetitionRepository competitionRepository, PouleRepository pouleRepository, RencontreRepository rencontreRepository) {
        this.competitionRepository = competitionRepository;
        this.pouleRepository = pouleRepository;
        this.rencontreRepository = rencontreRepository;
    }

    @GetMapping("")
    public String details(@PathVariable int id, Model model) {
        Competition c = competitionRepository.findById(id).orElse(null);
        assert c != null;


        // On recupere une map triant les equipes selon le numero de poule
        Collection<Poule> poules = this.pouleRepository.findAllByCompetitionId(c.getId());

        LinkedHashMap<Object, List<Poule>> equipesBasedOnPoule = poules.stream().
                collect(Collectors.groupingBy(Poule::getPouleNum,
                        LinkedHashMap::new,
                        toList()));

        // On recupere les poules deja inscrire sur cette compétion
        LinkedHashMap<Integer, List<Rencontre>> rencontreByPoule = this.rencontreRepository.findAllByCompetitonId(20)
                .stream()
                .collect(Collectors.groupingBy(
                        Rencontre::getPouleNumero,
                        LinkedHashMap::new,
                        toList()
                ));

        model.addAttribute("competition", c);
        model.addAttribute("poules", equipesBasedOnPoule);
        model.addAttribute("rencontreByPoule", rencontreByPoule);

        return "arbitre/competitionDetails";
    }

}
