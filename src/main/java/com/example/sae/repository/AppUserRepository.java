package com.example.sae.repository;

import com.example.sae.models.db.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String username);
}
