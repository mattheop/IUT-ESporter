package com.example.sae.controller.ecurie;

import com.example.sae.models.AppUser;
import com.example.sae.models.Joueur;
import com.example.sae.repository.JoueurRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ecurie/joueurs")
public class EcurieGestionJoueurController {
    private JoueurRepository joueurRepository;

    public EcurieGestionJoueurController(JoueurRepository joueurRepository) {
        this.joueurRepository = joueurRepository;
    }

    @GetMapping("/ajout")
    public String addPlayer(Model model) {
        Joueur j = new Joueur();
        model.addAttribute("joueur", j);

        return "ecurie/joueurs/ajout.html";
    }

    @PostMapping("/ajout")
    public String savePlayer(@ModelAttribute("joueur") Joueur joueur, Authentication authentication) {
        assert authentication != null;
        AppUser appUser = (AppUser) authentication.getPrincipal();

        joueur.setEcurie(AggregateReference.to(appUser.getManagedEcurieId()));

        this.joueurRepository.save(joueur);
        return "redirect:/ecurie";
    }
}
