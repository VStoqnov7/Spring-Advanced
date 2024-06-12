package com.example.mobilele.service;

import com.example.mobilele.models.dto.UserRegistrationDTO;
import com.example.mobilele.models.entity.User;

import java.util.Optional;


public interface UserService {
    void saveUser(UserRegistrationDTO userRegistrationDTO);

     /*void loginUser(UserLoginDTO userLoginDTO);*/

    Optional<User> findByUsername(String username);

     /*User findCurrentUser();*/
}



