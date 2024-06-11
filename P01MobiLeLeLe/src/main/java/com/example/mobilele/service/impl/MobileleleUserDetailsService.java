package com.example.mobilele.service.impl;

import com.example.mobilele.models.entity.UserRole;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.user.MobileleleUserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MobileleleUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MobileleleUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));
    }

    private UserDetails map(com.example.mobilele.models.entity.User user) {
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(this::mapRole)
                .collect(Collectors.toList());

        return new MobileleleUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                authorities
        );
    }

    private GrantedAuthority mapRole(UserRole userRoleEntity) {
        return new SimpleGrantedAuthority("ROLE_" + userRoleEntity.getName().name());
    }

}
