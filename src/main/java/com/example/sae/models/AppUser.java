package com.example.sae.models;

import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class AppUser implements UserDetails {

    @Id
    private int id;
    private String username;
    private String nom;
    private String prenom;
    private String password;
    private AppUserRole role;
    private Boolean locked;

    public AppUser() {
    }

    public AppUser(int id, String username, String nom, String prenom, String password, AppUserRole role, Boolean locked) {
        this.id = id;
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.role = role;
        this.locked = locked;
    }

    public AppUser(String username, String nom, String prenom, String password, AppUserRole role, Boolean locked) {
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.role = role;
        this.locked = locked;
    }

    public AppUser(String username, String nom, String prenom, String password, AppUserRole role) {
        this(username, nom, prenom, password, role, false);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.role.name());

        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", locked=" + locked +
                '}';
    }
}
