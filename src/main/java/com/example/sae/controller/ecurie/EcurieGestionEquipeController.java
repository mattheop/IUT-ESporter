package com.example.sae.controller.ecurie;

import com.example.sae.models.AppUser;
import com.example.sae.models.Equipe;
import com.example.sae.models.Jeu;
import com.example.sae.repository.EquipeRepository;
import com.example.sae.repository.JeuRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("ecurie/equipes")
public class EcurieGestionEquipeController {
    private EquipeRepository equipeRepository;
    private JeuRepository jeuRepository;

    public EcurieGestionEquipeController(EquipeRepository equipeRepository, JeuRepository jeuRepository) {
        this.equipeRepository = equipeRepository;
        this.jeuRepository = jeuRepository;
    }

    @GetMapping("/ajout")
    public String addEquipe(Model model, Authentication authentication) {
        assert authentication != null;
        AppUser appUser = (AppUser) authentication.getPrincipal();

        Equipe e = new Equipe();

        List<Jeu> jeux = this.jeuRepository.findAll();

        model.addAttribute("equipe", e);
        model.addAttribute("jeux", jeux);

        return "ecurie/equipes/ajout";
    }

    @PostMapping("/ajout")
    public String saveEquipe(@ModelAttribute("equipe") Equipe equipe, Authentication authentication) {
        assert authentication != null;
        AppUser appUser = (AppUser) authentication.getPrincipal();

        equipe.setEcurie(AggregateReference.to(appUser.getManagedEcurieId()));

        this.equipeRepository.save(equipe);
        return "redirect:/ecurie";
    }
}
