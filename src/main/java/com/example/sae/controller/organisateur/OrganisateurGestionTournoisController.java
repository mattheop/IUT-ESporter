package com.example.sae.controller.organisateur;

import com.example.sae.models.db.Competition;
import com.example.sae.models.db.Ecurie;
import com.example.sae.models.db.Inscription;
import com.example.sae.models.db.Tournois;
import com.example.sae.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("organisateur/tournois")
public class OrganisateurGestionTournoisController extends OrganisateurDashboard {

    private TournoisRepository tournoisRepository;
    private EcurieRepository ecurieRepository;
    private CompetitionRepository competitionRepository;
    private InscriptionRepository inscriptionRepository;
    private JeuRepository jeuRepository;

    public OrganisateurGestionTournoisController(TournoisRepository tournoisRepository, EcurieRepository ecurieRepository, CompetitionRepository competitionRepository, InscriptionRepository inscriptionRepository, JeuRepository jeuRepository) {
        this.tournoisRepository = tournoisRepository;
        this.ecurieRepository = ecurieRepository;
        this.competitionRepository = competitionRepository;
        this.inscriptionRepository = inscriptionRepository;
        this.jeuRepository = jeuRepository;
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


        Collection<Competition> competitions = competitionRepository.findByTournoisId(tournois.getId());
        Collection<Inscription> inscriptions = inscriptionRepository.findAllByTournoisId(tournois.getId());
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
    public String savePlayer(@ModelAttribute("tournois") @Valid Tournois tournoi, BindingResult tournoiBindingResult) {
        if (tournoiBindingResult.hasErrors()) {
            return "organisateur/tournois/create";
        }

        this.tournoisRepository.save(tournoi);
        return "redirect:/organisateur/tournois";
    }

    @GetMapping("/{id}/ajouterCompetition")
    public String addCompetitionForm(@PathVariable int id, Model model) {
        Tournois tournois = tournoisRepository.getTournoisById(id);
        Competition competition = new Competition();

        model.addAttribute("tournois", tournois);
        model.addAttribute("competition", competition);
        model.addAttribute("jeux", this.jeuRepository.findAll());

        return "organisateur/tournois/addCompetition";
    }

    @PostMapping("/{id}/ajouterCompetition")
    public String addCompetition(@PathVariable int id,
                                 @ModelAttribute("competition") @Valid Competition competition,
                                 BindingResult competitionBindingResult,
                                 Model model) {

        System.out.println(competition);
        System.out.println(competitionBindingResult.getAllErrors());
        if (competitionBindingResult.hasErrors()) {
            model.addAttribute("tournois", tournoisRepository.getTournoisById(id));
            model.addAttribute("competition", competition);
            model.addAttribute("jeux", this.jeuRepository.findAll());

            return "organisateur/tournois/addCompetition";
        }


        competitionRepository.insertDirect(competition.getDateDebutCompetition(),
                competition.getDateFinInscription(),
                competition.getJeu().getId(),
                id);
        return "redirect:/organisateur/tournois/" + id + "/details";
    }
}
