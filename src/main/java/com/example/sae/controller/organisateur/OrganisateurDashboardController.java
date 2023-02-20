package com.example.sae.controller.organisateur;

import com.example.sae.controller.ecurie.EcurieDashboard;
import com.example.sae.repository.CompetitionRepository;
import com.example.sae.repository.TournoisRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("organisateur")
public class OrganisateurDashboardController extends OrganisateurDashboard {


    private TournoisRepository tournoisRepository;

    public OrganisateurDashboardController(TournoisRepository tournoisRepository) {
        this.tournoisRepository = tournoisRepository;
    }

    @GetMapping()
    public String home(Model model) {
        model.addAttribute("nb_tournoi", this.tournoisRepository.getAllTournois().size());
        model.addAttribute("nb_mondial", this.tournoisRepository.getTournoisByEtenduetournoisMondial("Mondial"));
        model.addAttribute("nb_regional", this.tournoisRepository.getTournoisByEtenduetournoisRegional("Regional"));
        model.addAttribute("nb_national", this.tournoisRepository.getTournoisByEtenduetournoisNational("National"));
        return "organisateur/home";
    }

}
