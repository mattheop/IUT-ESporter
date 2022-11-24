package com.example.sae.Controller;

import com.example.sae.models.AppUser;
import com.example.sae.models.Ecurie;
import com.example.sae.models.Joueur;
import com.example.sae.repository.EcurieRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping("ecurie")
public class EcurieDashboardController {

    final
    private EcurieRepository ecurieRepository;

    public EcurieDashboardController(EcurieRepository ecurieRepository) {
        this.ecurieRepository = ecurieRepository;
    }

    @GetMapping()
    public String home(Authentication authentication, Model model) {
        assert authentication != null;
        AppUser appUser = (AppUser) authentication.getPrincipal();

        Ecurie managedEcurie = this.ecurieRepository.findById(appUser.getManagedEcurieId()).orElse(null);
        assert managedEcurie != null;
        Collection<Joueur> joueurs = this.ecurieRepository.getJoueurs(managedEcurie.getId());


        model.addAttribute("appuser", appUser);
        model.addAttribute("ecurie", managedEcurie);
        model.addAttribute("joueurs", joueurs);

        return "ecurie/home";
    }
}
