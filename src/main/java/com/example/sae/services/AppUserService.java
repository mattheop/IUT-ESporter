package com.example.sae.services;

import com.example.sae.exceptions.UserNotEcurieException;
import com.example.sae.models.db.AppUser;
import com.example.sae.models.db.Ecurie;
import com.example.sae.repository.AppUserRepository;
import com.example.sae.repository.CompetitionRepository;
import com.example.sae.repository.EcurieRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;
    private EcurieRepository ecurieRepository;

    public AppUserService(AppUserRepository appUserRepository, EcurieRepository ecurieRepository) {
        this.appUserRepository = appUserRepository;
        this.ecurieRepository = ecurieRepository;
    }

    @Override
    public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    public Ecurie getManagedEcurie() throws UserNotEcurieException {
        AppUser appUser = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return this.ecurieRepository.findById(appUser.getManagedEcurieId()).orElseThrow(UserNotEcurieException::new);
    }
}
