package com.example.mobilele.service;

import com.example.mobilele.models.dto.UserRegistrationDTO;
import com.example.mobilele.models.entity.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import java.util.Optional;
import java.util.function.Consumer;


public interface UserService {
    void saveUser(UserRegistrationDTO userRegistrationDTO);

     /*void loginUser(UserLoginDTO userLoginDTO);*/

    Optional<User> findByUsername(String username);

     /*User findCurrentUser();*/
}



