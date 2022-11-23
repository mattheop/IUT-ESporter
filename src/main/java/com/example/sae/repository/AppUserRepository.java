package com.example.sae.repository;

import com.example.sae.models.AppUser;
import com.example.sae.models.Joueur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String username);
}
