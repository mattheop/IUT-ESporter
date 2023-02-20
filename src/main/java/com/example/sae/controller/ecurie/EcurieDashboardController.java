package com.example.sae.controller.ecurie;

import com.example.sae.models.*;
import com.example.sae.models.db.Ecurie;
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
public class EcurieDashboardController extends EcurieDashboard {
    private JoueurRepository joueurRepository;
    private EquipeRepository equipeRepository;

    public EcurieDashboardController(JoueurRepository joueurRepository, EquipeRepository equipeRepository) {
        this.joueurRepository = joueurRepository;
        this.equipeRepository = equipeRepository;
    }

    @GetMapping()
    public String home(@ModelAttribute("ecurie") Ecurie ecurie, Model model) {
        model.addAttribute("nb_joueur", this.joueurRepository.countJoueurByEcurie_Id(ecurie.getId()));
        model.addAttribute("nb_equipe", this.equipeRepository.countEquipeByEcurie_Id(ecurie.getId()));
        return "ecurie/home";
    }


}
