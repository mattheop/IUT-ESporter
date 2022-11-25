package com.example.sae.controller;

import com.example.sae.models.AppUser;
import com.example.sae.models.Ecurie;
import com.example.sae.models.Equipe;
import com.example.sae.models.Joueur;
import com.example.sae.repository.EcurieRepository;
import com.example.sae.repository.EquipeRepository;
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
import java.util.Set;

@Controller
@RequestMapping("ecurie")
public class EcurieDashboardController {

    final private EcurieRepository ecurieRepository;
    final private JoueurRepository joueurRepository;

    public EcurieDashboardController(EcurieRepository ecurieRepository, JoueurRepository joueurRepository) {
        this.ecurieRepository = ecurieRepository;
        this.joueurRepository = joueurRepository;
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
    public String addPlayer(Model model){
        Joueur j = new Joueur();
        model.addAttribute("joueur", j);

        return "ecurie/joueurs/ajout.html";
    }

    @PostMapping("/joueurs/ajout")
    public String savePlayer(@ModelAttribute("joueur") Joueur joueur, Authentication authentication){
        assert authentication != null;
        AppUser appUser = (AppUser) authentication.getPrincipal();

        joueur.setEcurie(AggregateReference.to(appUser.getManagedEcurieId()));

        this.joueurRepository.save(joueur);
        return "redirect:/ecurie";
    }
}
