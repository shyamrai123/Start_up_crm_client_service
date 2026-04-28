package com.example.Start_up_crm_client_service.security;

import com.example.Start_up_crm_client_service.entity.ClientHr;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    @Getter
    private final Long id;

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    @Getter
    private final Set<Role> roles;



    // ✅ Constructor for HR
    public CustomUserDetails(ClientHr hr) {
        this.id = hr.getId();
        this.username = hr.getEmail();
        this.password = hr.getPassword();
        this.roles = null; // HR may not have Role entity
        this.authorities = List.of(
                new SimpleGrantedAuthority("ROLE_ORG")
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return username; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
