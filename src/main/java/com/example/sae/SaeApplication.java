package com.example.sae;

import com.example.sae.models.AppUser;
import com.example.sae.models.AppUserRole;
import com.example.sae.models.Joueur;
import com.example.sae.repository.AppUserRepository;
import com.example.sae.repository.JoueurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SaeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaeApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(JoueurRepository joueurRepository, AppUserRepository appUserRepository) {
        return args -> {

            // joueurRepository.save(new Joueur("Jean", "Pierre", "Jp78", "Francaise"));
        };
    }
}
