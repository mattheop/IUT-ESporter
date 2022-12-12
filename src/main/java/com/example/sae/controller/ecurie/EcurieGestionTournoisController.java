package com.example.sae.controller.ecurie;

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
@RequestMapping("ecurie/tournois")
public class EcurieGestionTournoisController extends EcurieDashboard {

    private TournoisRepository tournoisRepository;

    public EcurieGestionTournoisController(TournoisRepository tournoisRepository) {
        this.tournoisRepository = tournoisRepository;
    }

    @GetMapping()
    public String index(Model model, Authentication authentication) {
        Collection<Tournois> tournois = this.tournoisRepository.getAllTournois();

        model.addAttribute("tournois", tournois);

        return "ecurie/tournois/list";
    }

    @GetMapping("/create")
    public String createTournois(Model model) {
        Tournois tournois = new Tournois();

        model.addAttribute("tournois", tournois);

        return "ecurie/tournois/create";
    }

    @PostMapping("/create")
    public String savePlayer(@ModelAttribute("tournois") Tournois tournois, Authentication authentication) {

        this.tournoisRepository.save(tournois);
        return "redirect:/ecurie/tournois";
    }
}
