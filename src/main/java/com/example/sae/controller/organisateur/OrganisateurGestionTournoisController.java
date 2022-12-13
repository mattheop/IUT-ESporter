package com.example.sae.controller.organisateur;

import com.example.sae.controller.ecurie.EcurieDashboard;
import com.example.sae.models.Ecurie;
import com.example.sae.models.Joueur;
import com.example.sae.models.Tournois;
import com.example.sae.repository.TournoisRepository;
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
@RequestMapping("organisateur/tournois")
public class OrganisateurGestionTournoisController extends OrganisateurDashboard {

    private TournoisRepository tournoisRepository;

    public OrganisateurGestionTournoisController(TournoisRepository tournoisRepository) {
        this.tournoisRepository = tournoisRepository;
    }

    @GetMapping()
    public String index(Model model, Authentication authentication) {
        Collection<Tournois> tournois = this.tournoisRepository.getAllTournois();

        model.addAttribute("tournois", tournois);

        return "organisateur/tournois/list";
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
