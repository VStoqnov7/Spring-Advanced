package com.example.mobilele.service.impl;

import com.example.mobilele.models.dto.UserLoginDTO;
import com.example.mobilele.models.dto.UserRegistrationDTO;
import com.example.mobilele.models.entity.User;
import com.example.mobilele.models.user.CurrentUser;
import com.example.mobilele.repository.UserRepository;
import com.example.mobilele.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CurrentUser currentUser;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, CurrentUser currentUser) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.currentUser = currentUser;
    }

    @Override
    public void saveUser(UserRegistrationDTO userRegistrationDTO) {
        User user = this.modelMapper.map(userRegistrationDTO,User.class);
        user.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));
        user.setCreated(LocalDateTime.now());
        this.userRepository.saveAndFlush(user);
    }

    @Override
    public void loginUser(UserLoginDTO userLoginDTO) {
        this.currentUser.setUsername(userLoginDTO.getUsername());
        this.currentUser.setLoggedIn(true);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User findCurrendUser() {
        return this.userRepository.findByUsername(currentUser.getUsername());
    }
}
