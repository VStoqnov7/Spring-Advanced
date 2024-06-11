package com.example.mobilele.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MobileleleUserDetails implements UserDetails {

    private final String id;
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Collection<GrantedAuthority> authorities;

    public MobileleleUserDetails(String id, String username, String password, String firstName, String lastName, Collection<GrantedAuthority> authorities) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {

        StringBuilder fullName = new StringBuilder();

        if (this.getFirstName() != null) {
            fullName.append(this.getFirstName());
        }

        if (this.getLastName() != null) {
            if (fullName.length() > 0) {
                fullName.append(" ");
            }
            fullName.append(this.getLastName());
        }

        return fullName.toString().trim();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}