package com.example.mobilele.models.dto;

import com.example.mobilele.validations.uniqueUsername.UniqueUsername;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class UserRegistrationDTO {

    @Size(min = 3, max = 20, message = "First name length must be between 3 and 20 characters!")
    private String firstName;

    @Size(min = 3, max = 20, message = "Last name length must be between 3 and 20 characters!")
    private String lastName;

    @UniqueUsername
    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    private String username;

    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String password;

}