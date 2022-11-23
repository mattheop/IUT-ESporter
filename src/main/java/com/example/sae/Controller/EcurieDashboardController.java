package com.example.sae.Controller;

import com.example.sae.models.AppUser;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class EcurieDashboardController {

    @GetMapping()
    public String home(Authentication authentication) {

        AppUser appUser = (AppUser) authentication.getPrincipal();

        authentication.getAuthorities();

        return authentication.getAuthorities().toString();
    }

    @GetMapping("/ecurie")
    public String ecurieDash() {
        return "ok";
    }
}
