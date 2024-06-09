package com.example.mobilele.service;

import com.example.mobilele.models.dto.UserLoginDTO;
import com.example.mobilele.models.dto.UserRegistrationDTO;
import com.example.mobilele.models.entity.User;


public interface UserService {
    void saveUser(UserRegistrationDTO userRegistrationDTO);

    void loginUser(UserLoginDTO userLoginDTO);

    User findByUsername(String username);

    User findCurrendUser();
}



