package com.example.sae.Controller;

import com.example.sae.models.AppUser;
import com.example.sae.models.Ecurie;
import com.example.sae.repository.EcurieRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ecurie")
public class EcurieDashboardController {

    final
    EcurieRepository repository;

    public EcurieDashboardController(EcurieRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public String home(Authentication authentication, Model model) {

        if(authentication != null){
            AppUser appUser = (AppUser) authentication.getPrincipal();
            model.addAttribute("appuser", appUser);

            Ecurie managedEcurie = this.repository.findById(appUser.getManagedEcurieId()).orElse(null);

            model.addAttribute("ecurie", managedEcurie);
        }

        return "ecurie/home";
    }
}
