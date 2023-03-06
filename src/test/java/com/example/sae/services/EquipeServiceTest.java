package com.example.sae.services;

import com.example.sae.exceptions.equipe.EquipeNotFoundException;
import com.example.sae.exceptions.equipe.EquipeNotOwnedException;
import com.example.sae.exceptions.equipe.EquipeUploadLogoException;
import com.example.sae.exceptions.joueur.JoueurNotFoundException;
import com.example.sae.models.db.Equipe;
import com.example.sae.models.db.Joueur;
import com.example.sae.repository.EquipeRepository;
import com.example.sae.repository.JoueurRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Collection;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@ActiveProfiles("testing")
@Transactional
public class EquipeServiceTest {
    @Autowired
    private EquipeService equipeService;

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    JoueurRepository joueurRepository;

    private Equipe equipeFixture;

    @BeforeEach
    void setUp() {
        this.equipeFixture = new Equipe();
        this.equipeFixture.setNom("Equipe test unit");
        this.equipeFixture.setEcurie(AggregateReference.to(1));
        this.equipeFixture.setJeuSpe(1);
    }

    @AfterEach
    void tearDown() {
        this.equipeFixture = null;
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    void Save_WithValidModel_ShouldInsertToDatabase() {
        Equipe equipe = equipeService.save(this.equipeFixture);

        assertNotNull(equipe.getId());
    }

    @Test
    @WithUserDetails(value = "testing_ecurie2", userDetailsServiceBeanName = "appUserService")
    void Save_NotOwnedEquipe_ShouldRaiseNotOwnedEquipe() {
        equipeRepository.save(this.equipeFixture);
        assertThrows(EquipeNotOwnedException.class, () -> equipeService.save(this.equipeFixture));
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    void Save_WhenUpdate_ShouldUpdateDatabase() {
        this.equipeService.save(this.equipeFixture);
        assertNotNull(this.equipeFixture.getId());

        this.equipeFixture.setNom("Change");
        this.equipeService.save(this.equipeFixture);

        Equipe modified = this.equipeService.find(equipeFixture.getId());
        assertAll(() -> {
            assertEquals("Change", modified.getNom());
        });
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void Find_WhenIDIsValid_ShouldReturnEquipe() {
        this.equipeService.save(this.equipeFixture);
        int createdId = this.equipeFixture.getId();

        Equipe joueurFounded = this.equipeService.find(createdId);
        assertEquals(this.equipeFixture, joueurFounded);
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void Find_WhenIDNotValid_ShouldRaiseEquipeNotFound() {
        assertThrows(EquipeNotFoundException.class, () -> this.equipeService.find(-1));
        assertThrows(EquipeNotFoundException.class, () -> this.equipeService.find(999999999));
    }

    @Test
    @WithUserDetails(value = "testing_ecurie2", userDetailsServiceBeanName = "appUserService")
    public void Delete_WhenNotOwnedEquipe_ShouldRaiseEquipeNotOwned() {
        this.equipeRepository.save(this.equipeFixture);
        int createdId = this.equipeFixture.getId();

        assertThrows(EquipeNotOwnedException.class, () -> this.equipeService.delete(createdId));
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void Delete_WhenValid_ShouldNotExistAnymore() {
        this.equipeService.save(this.equipeFixture);
        int createdId = this.equipeFixture.getId();

        assertNotNull(this.equipeFixture.getId());

        this.equipeService.delete(createdId);

        assertThrows(EquipeNotFoundException.class, () -> this.equipeService.find(createdId));
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void FindAll_ShouldReturnCollectionOfAllEquipes() {
        this.equipeService.save(this.equipeFixture);
        Collection<Equipe> equipes = this.equipeService.findAll();
        assertFalse(equipes.isEmpty());

        Equipe equipeFixture2 = new Equipe();
        equipeFixture2.setNom("Equipe test unit2");
        equipeFixture2.setEcurie(AggregateReference.to(1));
        equipeFixture2.setJeuSpe(1);
        this.equipeService.save(equipeFixture2);

        assertEquals(equipes.size() + 1, this.equipeService.findAll().size());
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void SaveLogo_WithValidImage_ShouldUploadFile() throws IOException {
        equipeService.save(equipeFixture);

        byte[] fileContent = {1, 2, 3, 4, 5};
        MultipartFile multipartFile = new MockMultipartFile("logo.png", "logo.png", "image/png", fileContent);

        String expectedFileName = this.equipeFixture.getId() + "-eid@1_Equipe_test_unit.png";

        Path filePath = Paths.get(equipeService.getLogoUploadPath() + expectedFileName);
        Files.deleteIfExists(filePath);

        equipeService.saveLogo(equipeFixture, multipartFile);


        byte[] actualFileContent = Files.readAllBytes(filePath);
        assertEquals("/uploads/equipes_logo/" + expectedFileName, equipeFixture.getLogoPath());
        assertArrayEquals(fileContent, actualFileContent);
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void SaveLogo_WithInvalidImage_ShouldRaiseEquipeUploadLogoException() throws IOException {
        equipeService.save(equipeFixture);

        byte[] fileContent = "{[broken".getBytes(UTF_8);
        MultipartFile multipartFile = new InvalidMockMultipartFile("logo.png", "logo.png", "image/png", fileContent);

        String expectedFileName = this.equipeFixture.getId() + "-eid@1_Equipe_test_unit.png";

        Path filePath = Paths.get(equipeService.getLogoUploadPath() + expectedFileName);
        Files.deleteIfExists(filePath);

        assertAll(() -> {
            assertThrows(EquipeUploadLogoException.class, () -> equipeService.saveLogo(equipeFixture, multipartFile));
            assertFalse(Files.exists(filePath));
        });
    }
    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void AddJoueur_WithValidId_ShouldAddJoueur() {
        Joueur fake = new Joueur();
        fake.setEcurie(AggregateReference.to(1));
        fake.setPrenom("Jean");
        fake.setNom("PIERRE");
        fake.setPseudo("Fake");
        fake.setNationnalite("Francaise");
        fake.setEntree_pro(LocalDate.now());

        joueurRepository.save(fake);
        equipeService.save(equipeFixture);
        this.equipeService.addJoueur(equipeFixture, fake.getId());

        Equipe actual = this.equipeService.find(equipeFixture.getId());
        assertTrue(actual.getJoueursIds().stream()
                .anyMatch(joueurRef -> joueurRef.getJoueurId() == fake.getId()));

    }
    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void AddJoueur_WithInvalidId_ShouldRaiseJoueurNotFound() {
        equipeService.save(equipeFixture);
        assertThrows(JoueurNotFoundException.class, () ->  this.equipeService.addJoueur(equipeFixture, 99999));
    }

    @Test
    @WithUserDetails(value = "testing_ecurie", userDetailsServiceBeanName = "appUserService")
    public void RemoveJoueur_WithValidId_ShouldDeleteFromEquipe() {
        Joueur fake = new Joueur();
        fake.setEcurie(AggregateReference.to(1));
        fake.setPrenom("Jean");
        fake.setNom("PIERRE");
        fake.setPseudo("Fake");
        fake.setNationnalite("Francaise");
        fake.setEntree_pro(LocalDate.now());

        Joueur fake2 = new Joueur();
        fake2.setEcurie(AggregateReference.to(1));
        fake2.setPrenom("Jean2");
        fake2.setNom("PIERRE2");
        fake2.setPseudo("Fake2");
        fake2.setNationnalite("Francaise");
        fake2.setEntree_pro(LocalDate.now());

        joueurRepository.save(fake);
        joueurRepository.save(fake2);

        equipeFixture.addJoueur(fake);
        equipeFixture.addJoueur(fake2);
        equipeService.save(equipeFixture);

        assertEquals(2, equipeFixture.getJoueursIds().size());

        equipeService.removeJoueur(equipeFixture, fake2.getId());

        assertAll(() -> {
            assertEquals(1, equipeFixture.getJoueursIds().size());
            assertFalse(equipeFixture.getJoueursIds().stream().anyMatch(joueurRef -> joueurRef.getJoueurId() == fake2.getId()));
        });

    }

    private class InvalidMockMultipartFile extends MockMultipartFile {

        public InvalidMockMultipartFile(String name, String originalFilename, String contentType, byte[] content) {
            super(name, originalFilename, contentType, content);
        }

        @Override
        public byte[] getBytes() throws IOException {
            throw new IOException();
        }
    }
}
