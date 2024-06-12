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

    public MobileleleUserDetailsService(UserRepository userRepository) {
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

        authorities.forEach(auth -> System.out.println("Authority: " + auth.getAuthority()));

        MobileleleUserDetails mobileleleUserDetails = new MobileleleUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                authorities);
        return mobileleleUserDetails;
    }

    private GrantedAuthority mapRole(UserRole userRoleEntity) {
        String roleName = "ROLE_" + userRoleEntity.getName().name();
        System.out.println("Mapping role: " + roleName);
        return new SimpleGrantedAuthority(roleName);
    }

}
