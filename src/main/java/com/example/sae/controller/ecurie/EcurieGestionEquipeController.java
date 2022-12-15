package com.example.sae.controller.ecurie;

import com.example.sae.models.Ecurie;
import com.example.sae.models.Equipe;
import com.example.sae.models.Jeu;
import com.example.sae.models.Joueur;
import com.example.sae.models.ref.JoueurRef;
import com.example.sae.repository.EquipeRepository;
import com.example.sae.repository.JeuRepository;
import com.example.sae.repository.JoueurRepository;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("ecurie/equipes")
public class EcurieGestionEquipeController extends EcurieDashboard {
    private EquipeRepository equipeRepository;
    private JeuRepository jeuRepository;
    private JoueurRepository joueurRepository;

    public EcurieGestionEquipeController(EquipeRepository equipeRepository, JeuRepository jeuRepository, JoueurRepository joueurRepository) {
        this.equipeRepository = equipeRepository;
        this.jeuRepository = jeuRepository;
        this.joueurRepository = joueurRepository;
    }

    @GetMapping()
    public String index(@ModelAttribute("ecurie") Ecurie ecurie, Model model, Authentication authentication) {
        Set<Equipe> managedEquipes = ecurie.getEquipes();

        System.out.println(this.jeuRepository.findAll());

        Map<Integer, Jeu> all = jeuRepository.findAll().stream().collect(Collectors.toMap(
                Jeu::getId,
                Function.identity()
        ));
        managedEquipes.forEach(equipe -> equipe.setJeuSpeModel(all.get(equipe.getJeuSpe())));

        model.addAttribute("equipes", managedEquipes);

        return "ecurie/equipes/list";
    }

    @GetMapping("/{id}/details")
    public String detailsEquipe(@PathVariable Integer id, Model model, Authentication authentication) {
        Equipe e = equipeRepository.findById(id).orElse(null);

        if (e == null)
            return "redirect:/ecurie";

        Jeu jeuspe = jeuRepository.findById(Integer.valueOf(e.getJeuSpe())).orElse(null);
        Iterable<Joueur> joueurs = joueurRepository.findAllById(e.getJoueursIds().stream().map(JoueurRef::getJoueurId).collect(Collectors.toList()));
        Iterable<Joueur> allAvailableJoueurs = joueurRepository.getJoueursOwnedWithoutTeam(e.getEcurie().getId());

        model.addAttribute("equipe", e);
        model.addAttribute("jeuspe", jeuspe);
        model.addAttribute("joueurs", joueurs);
        model.addAttribute("allAvailableJoueurs", allAvailableJoueurs);

        return "ecurie/equipes/details";
    }

    @PostMapping("/{id}/ajouterJoueur")
    public String ajouterJoueur(@PathVariable Integer id, @RequestParam(value = "id_joueur") Integer id_joueur) {

        Equipe e = equipeRepository.findById(id).orElse(null);
        assert e != null;
        e.addJoueur(new JoueurRef(id_joueur));

        this.equipeRepository.save(e);
        return "redirect:/ecurie/equipes/" + e.getId() + "/details";
    }

    @PostMapping("/{id}/supprimerJoueur")
    public String supprimerJoueur(@PathVariable Integer id, @RequestParam(value = "id_joueur") Integer id_joueur) {

        Equipe e = equipeRepository.findById(id).orElse(null);
        assert e != null;
        e.removeJoueur(id_joueur);

        this.equipeRepository.save(e);
        return "redirect:/ecurie/equipes/" + e.getId() + "/details";
    }


    @GetMapping("/ajout")
    public String addEquipe(Model model, Authentication authentication) {
        Equipe e = new Equipe();

        List<Jeu> jeux = this.jeuRepository.findAll();

        model.addAttribute("equipe", e);
        model.addAttribute("jeux", jeux);

        return "ecurie/equipes/ajout";
    }

    @PostMapping("/ajout")
    public String saveEquipe(@ModelAttribute("equipe") Equipe equipe, @ModelAttribute("ecurie") Ecurie ecurie, Authentication authentication) {
        equipe.setEcurie(AggregateReference.to(ecurie.getId()));

        this.equipeRepository.save(equipe);
        return "redirect:/ecurie";
    }

    @PostMapping("/{id}/supprimer")
    public String saveEquipe(@PathVariable("id") Integer id) {
        this.equipeRepository.deleteById(id);
        return "redirect:/ecurie/equipes";
    }
}
