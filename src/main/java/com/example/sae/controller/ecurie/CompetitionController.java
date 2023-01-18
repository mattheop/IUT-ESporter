package com.example.sae.controller.ecurie;

import com.example.sae.models.db.*;
import com.example.sae.repository.*;
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
    private EquipeRepository equipeRepository;
    private InscriptionRepository inscriptionRepository;
    private RencontreRepository rencontreRepository;

    public CompetitionController(CompetitionRepository competitionRepository,
                                 PouleRepository pouleRepository,
                                 EquipeRepository equipeRepository,
                                 InscriptionRepository inscriptionRepository,
                                 RencontreRepository rencontreRepository) {
        this.competitionRepository = competitionRepository;
        this.pouleRepository = pouleRepository;
        this.equipeRepository = equipeRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.rencontreRepository = rencontreRepository;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable int id, Model model) {
        Competition c = competitionRepository.findById(id).orElse(null);
        assert c != null;


        // On recupere une map triant les equipes selon le numero de poule
        Collection<Poule> poules = this.pouleRepository.findAllByCompetitionId(c.getId());
        Map<Object, List<Poule>> equipesBasedOnPoule = poules.stream().
                collect(Collectors.groupingBy(Poule::getPouleNum));

        // On recuprer les equipes qui pevent s'insrire a cette compétition
        Map<Integer, List<Equipe>> equipesByJeuSpeID = this.equipeRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Equipe::getJeuSpe,
                        Collectors.toList()
                ));

        // On recupere les poules deja inscrire sur cette compétion
        List<Inscription> inscriptionList = this.inscriptionRepository.findAllByCompetitionId(c.getId());

        Map<Integer, List<Rencontre>> rencontreByPoule = this.rencontreRepository.findAllByCompetitonId(c.getId())
                .stream()
                .collect(Collectors.groupingBy(
                        Rencontre::getPouleNumero
                ));

        // On crée un model vierge pour une eventulle inscription
        Inscription newInscription = new Inscription();

        model.addAttribute("equipesByJeuSpeID", equipesByJeuSpeID);
        model.addAttribute("newInscriptionModel", newInscription);


        model.addAttribute("competition", c);
        model.addAttribute("inscriptions", inscriptionList);
        model.addAttribute("poules", equipesBasedOnPoule);
        model.addAttribute("rencontreByPoule", rencontreByPoule);

        return "ecurie/competition/details";
    }

}
