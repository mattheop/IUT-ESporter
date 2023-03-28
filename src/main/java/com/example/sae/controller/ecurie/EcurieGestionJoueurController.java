package com.example.sae.controller.ecurie;

import com.example.sae.exceptions.joueur.JoueurNotFoundException;
import com.example.sae.exceptions.joueur.JoueurNotOwnedException;
import com.example.sae.exceptions.UserNotEcurieException;
import com.example.sae.models.db.Ecurie;
import com.example.sae.models.db.Joueur;
import com.example.sae.repository.EcurieRepository;
import com.example.sae.repository.JoueurRepository;
import com.example.sae.services.JoueurService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collection;

@Controller
@RequestMapping("ecurie/joueurs")
public class EcurieGestionJoueurController extends EcurieDashboard {
    private EcurieRepository ecurieRepository;
    private JoueurService joueurService;
    public EcurieGestionJoueurController(EcurieRepository ecurieRepository, JoueurService joueurService) {
        this.ecurieRepository = ecurieRepository;
        this.joueurService = joueurService;
    }

    @GetMapping()
    public String index(@ModelAttribute("ecurie") Ecurie ecurie, Model model) {
        Collection<Joueur> joueurs = this.ecurieRepository.getJoueurs(ecurie.getId());

        model.addAttribute("joueurs", joueurs);

        return "ecurie/joueurs/list";
    }

    @GetMapping("/ajout")
    public String addPlayer(Model model, @ModelAttribute("ecurie") Ecurie ecurie) {
        Joueur joueur = new Joueur();
        joueur.setEcurie(AggregateReference.to(ecurie.getId()));

        model.addAttribute("joueur", joueur);

        return "ecurie/joueurs/ajout";
    }

    @PostMapping("/ajout")
    public String savePlayer(@ModelAttribute("joueur") @Valid Joueur joueur, BindingResult joueurBindingResult, @ModelAttribute("ecurie") Ecurie ecurie) {
        if (joueurBindingResult.hasErrors()) {
            return "ecurie/joueurs/ajout";
        }

        joueurService.save(joueur);

        return "redirect:/ecurie/joueurs";
    }

    @GetMapping("/{id}")
    public String editPlayer(@PathVariable("id") int id, Model model) {
        model.addAttribute("joueur", this.joueurService.find(id));

        return "ecurie/joueurs/ajout";
    }

    /**
     * Route permettant de supprimer un joueur
     * @param id passé dans l'URL
     */
    @PostMapping("/{id}/supprimer")
    public String saveEquipe(@PathVariable("id") Integer id) {
        this.joueurService.delete(id);
        return "redirect:/ecurie/joueurs";
    }

    /**
     * Si l'exception JoueurNotFoundException est levée, alors on renvoie une 404 avec le message d'erreur
     */
    @ExceptionHandler(JoueurNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleJoueurNotFound(JoueurNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    /**
     * Si l'exception UserNotEcurieException est levée, alors on renvoie une 404 avec le message d'erreur
     */
    @ExceptionHandler({UserNotEcurieException.class, UserPrincipalNotFoundException.class, JoueurNotOwnedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleUserNotEcurie(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }
}
