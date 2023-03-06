package com.example.sae.controller.ecurie;

import com.example.sae.exceptions.UserNotEcurieException;
import com.example.sae.exceptions.equipe.EquipeNotFoundException;
import com.example.sae.exceptions.equipe.EquipeNotOwnedException;
import com.example.sae.exceptions.equipe.EquipeUploadLogoException;
import com.example.sae.exceptions.joueur.JoueurNotFoundException;
import com.example.sae.exceptions.joueur.JoueurNotOwnedException;
import com.example.sae.models.db.Ecurie;
import com.example.sae.models.db.Equipe;
import com.example.sae.models.db.Jeu;
import com.example.sae.models.db.Joueur;
import com.example.sae.models.ref.JoueurRef;
import com.example.sae.repository.JeuRepository;
import com.example.sae.repository.JoueurRepository;
import com.example.sae.services.EquipeService;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("ecurie/equipes")
public class EcurieGestionEquipeController extends EcurieDashboard {
    private EquipeService equipeServie;
    private JeuRepository jeuRepository;
    private JoueurRepository joueurRepository;

    public EcurieGestionEquipeController(EquipeService equipeServie, JeuRepository jeuRepository, JoueurRepository joueurRepository) {
        this.equipeServie = equipeServie;
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
        Equipe e = equipeServie.find(id);

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
    public String ajouterJoueur(@PathVariable Integer id, @RequestParam(value = "id_joueur") Integer idJoueur) {
        Equipe e = equipeServie.find(id);
        equipeServie.addJoueur(e, idJoueur);

        return "redirect:/ecurie/equipes/" + e.getId() + "/details";
    }

    @PostMapping("/{id}/supprimerJoueur")
    public String supprimerJoueur(@PathVariable Integer id, @RequestParam(value = "id_joueur") Integer idJoueur) {
        Equipe e = equipeServie.find(id);
        equipeServie.removeJoueur(e, idJoueur);

        return "redirect:/ecurie/equipes/" + e.getId() + "/details";
    }

    @PostMapping("/{id}/ajouterLogo")
    public String ajouterLogo(@PathVariable Integer id, @RequestParam("image") MultipartFile multipartFile) {
        Equipe equipe = equipeServie.find(id);
        equipeServie.saveLogo(equipe, multipartFile);

        return "redirect:/ecurie/equipes/" + equipe.getId() + "/details";
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
    public String saveEquipe(@ModelAttribute("equipe") @Valid Equipe equipe,
                             BindingResult equipeBindingResult,
                             @ModelAttribute("ecurie") Ecurie ecurie,
                             Model model) {

        if (equipeBindingResult.hasErrors()) {
            List<Jeu> jeux = this.jeuRepository.findAll();
            model.addAttribute("jeux", jeux);
            return "ecurie/equipes/ajout";
        }

        equipe.setEcurie(AggregateReference.to(ecurie.getId()));
        this.equipeServie.save(equipe);
        return "redirect:/ecurie";
    }

    @PostMapping("/{id}/supprimer")
    public String saveEquipe(@PathVariable("id") Integer id) {
        this.equipeServie.delete(id);
        return "redirect:/ecurie/equipes";
    }

    @ExceptionHandler(EquipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleEquipeNotFound(EquipeNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(EquipeUploadLogoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleEquipeLogoFailUpload(EquipeUploadLogoException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    /**
     * Si l'exception UserNotEcurieException est levée, alors on renvoie une 404 avec le message d'erreur
     */
    @ExceptionHandler({UserPrincipalNotFoundException.class, EquipeNotOwnedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handlePermissionIssue(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(exception.getMessage());
    }
}
