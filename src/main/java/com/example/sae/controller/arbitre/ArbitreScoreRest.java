package com.example.sae.controller.arbitre;

import com.example.sae.controller.arbitre.RESTBody.ArbitreScoreRESTBody;
import com.example.sae.models.db.Rencontre;
import com.example.sae.repository.CompetitionRepository;
import com.example.sae.repository.RencontreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController()
@RequestMapping("/arbitre/score")
public class ArbitreScoreRest {

    private RencontreRepository rencontreRepository;
    private CompetitionRepository competitionRepository;

    public ArbitreScoreRest(RencontreRepository rencontreRepository, CompetitionRepository competitionRepository) {
        this.rencontreRepository = rencontreRepository;
        this.competitionRepository = competitionRepository;
    }

    @GetMapping()
    public String index() {
        return "ae";
    }

    @PutMapping("")
    @ResponseBody
    public ResponseEntity<String> updateScore(@RequestBody ArbitreScoreRESTBody payload) {
        int updatedRows = this.rencontreRepository.updateScore(payload.getRencontreID(), payload.getEquipe1(), payload.getEquipe2());
        Collection<Rencontre> rencontres = this.rencontreRepository.findAllByCompetitonId(payload.getCompetitionID());

        long totalRecontreDone = rencontres.stream()
                .filter(rencontre -> rencontre.getScoreEquipe1() != null && rencontre.getScoreEquipe2() != null)
                .count();

        if (totalRecontreDone == Rencontre.TOTAL_RENCONTRE) {
            this.rencontreRepository.generateRencontres(payload.getCompetitionID(), 99);
        }

        if (updatedRows <= 0)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");

        return ResponseEntity.status(HttpStatus.OK).body("updated");

    }
}
