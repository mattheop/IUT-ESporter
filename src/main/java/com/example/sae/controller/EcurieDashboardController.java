package com.example.sae.controller;

import com.example.sae.models.*;
import com.example.sae.repository.EcurieRepository;
import com.example.sae.repository.EquipeRepository;
import com.example.sae.repository.JeuRepository;
import com.example.sae.repository.JoueurRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("ecurie")
public class EcurieDashboardController {

    final private EcurieRepository ecurieRepository;
    final private JoueurRepository joueurRepository;
    private JeuRepository jeuRepository;
    private EquipeRepository equipeRepository;

    public EcurieDashboardController(EcurieRepository ecurieRepository, JoueurRepository joueurRepository, JeuRepository jeuRepository, EquipeRepository equipeRepository) {
        this.ecurieRepository = ecurieRepository;
        this.joueurRepository = joueurRepository;
        this.jeuRepository = jeuRepository;
        this.equipeRepository = equipeRepository;
    }

    @GetMapping()
    public String home(Authentication authentication, Model model) {
        assert authentication != null;
        AppUser appUser = (AppUser) authentication.getPrincipal();

        Ecurie managedEcurie = this.ecurieRepository.findById(appUser.getManagedEcurieId()).orElse(null);
        assert managedEcurie != null;
        Collection<Joueur> joueurs = this.ecurieRepository.getJoueurs(managedEcurie.getId());

        Set<Equipe> managedEquipes = managedEcurie.getEquipes();


        model.addAttribute("appuser", appUser);
        model.addAttribute("ecurie", managedEcurie);

        model.addAttribute("equipes", managedEquipes);
        System.out.println(managedEquipes);
        model.addAttribute("joueurs", joueurs);

        return "ecurie/home";
    }

    @GetMapping("/joueurs/ajout")
    public String addPlayer(Model model) {
        Joueur j = new Joueur();
        model.addAttribute("joueur", j);

        return "ecurie/joueurs/ajout.html";
    }

    @PostMapping("/joueurs/ajout")
    public String savePlayer(@ModelAttribute("joueur") Joueur joueur, Authentication authentication) {
        assert authentication != null;
        AppUser appUser = (AppUser) authentication.getPrincipal();

        joueur.setEcurie(AggregateReference.to(appUser.getManagedEcurieId()));

        this.joueurRepository.save(joueur);
        return "redirect:/ecurie";
    }

    @GetMapping("/equipes/ajout")
    public String addEquipe(Model model, Authentication authentication) {
        assert authentication != null;
        AppUser appUser = (AppUser) authentication.getPrincipal();

        Equipe e = new Equipe();

        List<Jeu> jeux = this.jeuRepository.findAll();

        model.addAttribute("equipe", e);
        model.addAttribute("jeux", jeux);

        return "ecurie/equipes/ajout";
    }

    @PostMapping("/equipes/ajout")
    public String saveEquipe(@ModelAttribute("equipe") Equipe equipe, Authentication authentication) {
        assert authentication != null;
        AppUser appUser = (AppUser) authentication.getPrincipal();

        equipe.setEcurie(AggregateReference.to(appUser.getManagedEcurieId()));

        this.equipeRepository.save(equipe);
        return "redirect:/ecurie";
    }
}
