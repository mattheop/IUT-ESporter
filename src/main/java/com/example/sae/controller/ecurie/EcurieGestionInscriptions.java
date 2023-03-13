package com.example.sae.controller.ecurie;

import com.example.sae.models.db.Competition;
import com.example.sae.models.db.Inscription;
import com.example.sae.models.db.Tournois;
import com.example.sae.repository.CompetitionRepository;
import com.example.sae.repository.EquipeRepository;
import com.example.sae.repository.InscriptionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("ecurie/inscriptions")
public class EcurieGestionInscriptions extends EcurieDashboard {

    private CompetitionRepository competitionRepository;
    private InscriptionRepository inscriptionRepository;
    public EcurieGestionInscriptions(CompetitionRepository competitionRepository, InscriptionRepository inscriptionRepository) {
        this.competitionRepository = competitionRepository;
        this.inscriptionRepository = inscriptionRepository;
    }

    @GetMapping()
    public String showInscriptions(Model model) {
        Map<Tournois, List<Competition>> competitionsByTournois = competitionRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Competition::getTournois,
                        Collectors.toList()
                ));

        model.addAttribute("competitionsByTournois", competitionsByTournois);

        return "ecurie/inscriptions/list";
    }

    @PostMapping()
    public String inscrire(@ModelAttribute("inscription") Inscription inscription, HttpServletRequest request) {
        inscriptionRepository.insertDirect(inscription.getCompetition().getId(),
                inscription.getEquipe().getId());

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @PostMapping("/desinscrire")
    public String desinscrire(@RequestParam("inscriptionId") Integer id, HttpServletRequest request) {
        inscriptionRepository.deleteById(id);

        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
