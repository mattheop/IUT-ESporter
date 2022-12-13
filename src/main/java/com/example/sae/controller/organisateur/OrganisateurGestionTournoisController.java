package com.example.sae.controller.organisateur;

import com.example.sae.models.*;
import com.example.sae.repository.CompetitionRepository;
import com.example.sae.repository.EcurieRepository;
import com.example.sae.repository.InscriptionRepository;
import com.example.sae.repository.TournoisRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("organisateur/tournois")
public class OrganisateurGestionTournoisController extends OrganisateurDashboard {

    private TournoisRepository tournoisRepository;
    private EcurieRepository ecurieRepository;
    private CompetitionRepository competitionRepository;
    private InscriptionRepository inscriptionRepository;

    public OrganisateurGestionTournoisController(TournoisRepository tournoisRepository, EcurieRepository ecurieRepository, CompetitionRepository competitionRepository, InscriptionRepository inscriptionRepository) {
        this.tournoisRepository = tournoisRepository;
        this.ecurieRepository = ecurieRepository;
        this.competitionRepository = competitionRepository;
        this.inscriptionRepository = inscriptionRepository;
    }

    @GetMapping()
    public String index(Model model) {
        Collection<Tournois> tournois = this.tournoisRepository.getAllTournois();

        model.addAttribute("tournois", tournois);

        return "organisateur/tournois/list";
    }

    @GetMapping("/{id}/details")
    public String detailsTournoi(@PathVariable Integer id, Model model) {
        Tournois tournois = tournoisRepository.getTournoisById(id);

        if (tournois == null)
            return "redirect:/organisateur";

        Collection<Competition> competitions = competitionRepository.findAll().stream()
                .filter(competition -> competition.getTournois().getId().equals(id))
                .toList();

        Collection<Inscription> inscriptions = inscriptionRepository.findAll().stream()
                .filter(inscription -> inscription.getCompetition().getTournois().getId().equals(id))
                .toList();

        Collection<Ecurie> ecuries = ecurieRepository.findAll();

        inscriptions.forEach(inscription -> inscription.setLazyEcurie(
                ecuries.stream().filter(ecurie -> ecurie.getId() == inscription.getEquipe().getEcurie().getId())
                        .findFirst()
                        .orElse(null)
        ));


        System.out.println(ecuries);


        model.addAttribute("tournois", tournois);
        model.addAttribute("competitions", competitions);
        model.addAttribute("inscriptions", inscriptions);

        return "organisateur/tournois/details";
    }

    @GetMapping("/create")
    public String createTournois(Model model) {
        Tournois tournois = new Tournois();

        model.addAttribute("tournois", tournois);

        return "organisateur/tournois/create";
    }

    @PostMapping("/create")
    public String savePlayer(@ModelAttribute("tournois") Tournois tournois, Authentication authentication) {

        this.tournoisRepository.save(tournois);
        return "redirect:/organisateur/tournois";
    }
}
