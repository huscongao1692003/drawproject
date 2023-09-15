package com.drawproject.dev.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class DashboardResponseDTO {
    private String username;
    private Collection<? extends GrantedAuthority> roles;



    public DashboardResponseDTO(String username, Collection<? extends GrantedAuthority> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }
}
