package com.example.sae.controller.ecurie;

import com.example.sae.models.db.Ecurie;
import com.example.sae.models.db.Joueur;
import com.example.sae.repository.EcurieRepository;
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

@Controller
@RequestMapping("ecurie/joueurs")
public class EcurieGestionJoueurController extends EcurieDashboard {
    private JoueurRepository joueurRepository;
    private EcurieRepository ecurieRepository;

    public EcurieGestionJoueurController(JoueurRepository joueurRepository, EcurieRepository ecurieRepository) {
        this.joueurRepository = joueurRepository;
        this.ecurieRepository = ecurieRepository;
    }

    @GetMapping()
    public String index(@ModelAttribute("ecurie") Ecurie ecurie, Model model, Authentication authentication) {
        Collection<Joueur> joueurs = this.ecurieRepository.getJoueurs(ecurie.getId());

        model.addAttribute("joueurs", joueurs);

        return "ecurie/joueurs/list";
    }

    @GetMapping("/ajout")
    public String addPlayer(Model model) {
        Joueur j = new Joueur();
        model.addAttribute("joueur", j);

        return "ecurie/joueurs/ajout.html";
    }

    @PostMapping("/ajout")
    public String savePlayer(@ModelAttribute("joueur") Joueur joueur, @ModelAttribute("ecurie") Ecurie ecurie, Authentication authentication) {

        joueur.setEcurie(AggregateReference.to(ecurie.getId()));

        this.joueurRepository.save(joueur);
        return "redirect:/ecurie/joueurs";
    }
}
