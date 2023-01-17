package com.example.sae.controller.arbitre;

import com.example.sae.controller.arbitre.RESTBody.ArbitreScoreRESTBody;
import com.example.sae.repository.RencontreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/arbitre/score")
public class ArbitreScoreRest {

    private RencontreRepository rencontreRepository;

    public ArbitreScoreRest(RencontreRepository rencontreRepository) {
        this.rencontreRepository = rencontreRepository;
    }

    @GetMapping()
    public String index() {
        return "ae";
    }

    @PutMapping("")
    @ResponseBody
    public ResponseEntity<String> updateScore(@RequestBody ArbitreScoreRESTBody payload) {
        int updatedRows = this.rencontreRepository.updateScore(payload.getRencontreID(), payload.getEquipe1(), payload.getEquipe2());

        if (updatedRows <= 0)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");

        return ResponseEntity.status(HttpStatus.OK).body("updated");

    }
}
