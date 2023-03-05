package com.example.sae.services;

import com.example.sae.exceptions.UserNotEcurieException;
import com.example.sae.models.db.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("testing")
@Transactional
public class AppUserServiceTest {

    @Autowired
    private AppUserService appUserService;

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    void getManagedEcurie_whenEcurieRole_ShouldReturnManagedEcurieId() {
        int id = appUserService.getManagedEcurie().getId();

        assertEquals(1, id);
    }

    @Test
    @WithUserDetails(value = "testing_orgnisateur", userDetailsServiceBeanName = "appUserService")
    void getManagedEcurie_whenNotEcurieRole_ShouldRaiseUserNotEcurie() {
        assertThrows(UserNotEcurieException.class, () -> appUserService.getManagedEcurie().getId());
    }

    @Test
    void loadUserByUsername_whenValidUsername_ShouldReturnUserDetails() {
        AppUser appuser = appUserService.loadUserByUsername("testing_ecurie");
        assertNotNull(appuser);
    }

    @Test
    void loadUserByUsername_whenInvalidUsername_ShouldReturnUserDetails() {
        assertThrows(UsernameNotFoundException.class, () -> appUserService.loadUserByUsername("INVALIDE"));
    }
}
