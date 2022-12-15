package com.example.sae.controller.ecurie;

import com.example.sae.models.Competition;
import com.example.sae.models.Equipe;
import com.example.sae.models.Inscription;
import com.example.sae.models.Tournois;
import com.example.sae.repository.CompetitionRepository;
import com.example.sae.repository.EquipeRepository;
import com.example.sae.repository.InscriptionRepository;
import com.example.sae.repository.TournoisRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("ecurie/inscriptions")
public class EcurieGestionInscriptions extends EcurieDashboard {

    private CompetitionRepository competitionRepository;
    private InscriptionRepository inscriptionRepository;
    private EquipeRepository equipeRepository;

    public EcurieGestionInscriptions(CompetitionRepository competitionRepository, InscriptionRepository inscriptionRepository, EquipeRepository equipeRepository) {
        this.competitionRepository = competitionRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.equipeRepository = equipeRepository;
    }

    @GetMapping()
    public String showInscriptions(Model model) {
        Map<Tournois, List<Competition>> competitionsByTournois = competitionRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Competition::getTournois,
                        Collectors.toList()
                ));

        Map<Competition, List<Inscription>> inscriptionByCompetition = inscriptionRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Inscription::getCompetition,
                        Collectors.toList()
                ));

        Map<Integer, List<Equipe>> equipesByJeuSpeID = equipeRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Equipe::getJeuSpe,
                        Collectors.toList()
                ));

        Inscription newInscription = new Inscription();

        model.addAttribute("competitionsByTournois", competitionsByTournois);
        model.addAttribute("inscriptionByCompetition", inscriptionByCompetition);
        model.addAttribute("equipesByJeuSpeID", equipesByJeuSpeID);
        model.addAttribute("inscription", newInscription);

        return "ecurie/inscriptions/list";
    }

    @PostMapping()
    public String inscrire(@ModelAttribute("inscription") Inscription inscription) {
        inscriptionRepository.insertDirect(inscription.getCompetition().getId(),
                inscription.getEquipe().getId());
        return "redirect:/ecurie/inscriptions";
    }

    @PostMapping("/desinscrire")
    public String desinscrire(@RequestParam("inscriptionId") Integer id) {
        inscriptionRepository.deleteById(id);

        return "redirect:/ecurie/inscriptions";
    }
}
