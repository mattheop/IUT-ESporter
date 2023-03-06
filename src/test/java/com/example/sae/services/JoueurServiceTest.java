package com.example.sae.services;

import com.example.sae.exceptions.joueur.JoueurNotFoundException;
import com.example.sae.exceptions.joueur.JoueurNotOwnedException;
import com.example.sae.exceptions.joueur.JoueurShouldBeAttachedToEcurie;
import com.example.sae.models.db.Joueur;
import com.example.sae.repository.JoueurRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("testing")
@Transactional
public class JoueurServiceTest {
    @Autowired
    private JoueurService joueurService;

    @Autowired
    private JoueurRepository joueurRepository;
    private Joueur joueurFixture;

    @BeforeEach
    public void init() {
        this.joueurFixture = new Joueur();
        this.joueurFixture.setPrenom("john");
        this.joueurFixture.setNom("Doe");
        this.joueurFixture.setPseudo("Jojo");
        this.joueurFixture.setNationnalite("Francaise");
        this.joueurFixture.setEcurie(AggregateReference.to(1));
        this.joueurFixture.setEntree_pro(LocalDate.now());
    }

    @AfterEach
    public void down() {
        this.joueurFixture = null;
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void Save_WithNoEcurie_ShouldRaiseJoueurShouldBeAttachedToEcurie() {
        this.joueurFixture.setEcurie(null);
        assertThrows(JoueurShouldBeAttachedToEcurie.class, () -> joueurService.save(this.joueurFixture));
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void Save_ShouldAddToEcurie() {
        assertNull(this.joueurFixture.getId());

        this.joueurService.save(this.joueurFixture);

        assertNotNull(this.joueurFixture.getId());
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void Save_ShouldUpdateToDatabase() {
        this.joueurService.save(this.joueurFixture);

        assertAll(() -> {
            assertEquals("John", this.joueurFixture.getPrenom());
            assertEquals("DOE", this.joueurFixture.getNom());
        });
        this.joueurFixture.setNom("Nouveau nom");
        this.joueurFixture.setPrenom("Nouveau prenom");

        this.joueurService.save(this.joueurFixture);

        Joueur modified = joueurService.find(this.joueurFixture.getId());

        assertAll(() -> {
            assertEquals("Nouveau prenom", modified.getPrenom());
            assertEquals("NOUVEAU NOM", modified.getNom());
        });

    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void Save_NotFormattedNames_ShouldBeFormattedWhenSaved() {
        this.joueurService.save(this.joueurFixture);

        assertEquals("DOE", this.joueurFixture.getNom());
        assertEquals("John", this.joueurFixture.getPrenom());
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void Find_WhenIDIsValid_ShouldReturnJoueur() {
        this.joueurService.save(this.joueurFixture);
        int createdId = joueurFixture.getId();

        Joueur joueurFounded = this.joueurService.find(createdId);
        assertEquals(this.joueurFixture, joueurFounded);
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void Find_WhenIDNotValid_ShouldRaiseJoueurNotFound() {
        assertThrows(JoueurNotFoundException.class, () -> this.joueurService.find(-1));
        assertThrows(JoueurNotFoundException.class, () -> this.joueurService.find(999999999));
    }

    @Test
    @WithUserDetails(value = "testing_ecurie2", userDetailsServiceBeanName = "appUserService")
    public void Find_WhenNotOwnedJoueur_ShouldRaiseJouerNotOwned() {
        this.joueurRepository.save(this.joueurFixture);
        int createdId = joueurFixture.getId();

        assertThrows(JoueurNotOwnedException.class, () -> this.joueurService.find(createdId));
    }

    @Test
    @WithUserDetails(value = "testing_ecurie2", userDetailsServiceBeanName = "appUserService")
    public void Delete_WhenNotOwnedJoueur_ShouldRaiseJouerNotOwned() {
        this.joueurRepository.save(this.joueurFixture);
        int createdId = joueurFixture.getId();

        assertThrows(JoueurNotOwnedException.class, () -> this.joueurService.delete(createdId));
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void Delete_WhenValid_ShouldNotExistAnymore() {
        this.joueurService.save(this.joueurFixture);
        int createdId = joueurFixture.getId();

        this.joueurService.delete(createdId);

        assertThrows(JoueurNotFoundException.class, () -> this.joueurService.find(createdId));
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void FindAll_ShouldReturnCollectionOfAllJoueurs() {
        this.joueurService.save(this.joueurFixture);
        Collection<Joueur> joueurs = this.joueurService.findAll();
        assertFalse(joueurs.isEmpty());

        this.joueurFixture.setNom("Pierro");
        this.joueurFixture.setId(null);
        this.joueurService.save(this.joueurFixture);

        assertEquals(joueurs.size() + 1, this.joueurService.findAll().size());
    }

}
