package com.example.sae.controller.ecurie;

import com.example.sae.models.Rencontre;
import com.example.sae.repository.RencontreRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class Sand {
    private final RencontreRepository rencontreRepository;

    public Sand(RencontreRepository rencontreRepository) {
        this.rencontreRepository = rencontreRepository;
    }

    @GetMapping("/sand")
    public Map<Integer, List<Rencontre>> index() {
        return rencontreRepository.findAllByCompetitonId(20).stream().collect(Collectors.groupingBy(Rencontre::getPouleNumero));
    }
}
