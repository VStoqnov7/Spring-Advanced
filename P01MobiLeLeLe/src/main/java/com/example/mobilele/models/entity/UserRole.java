package com.example.mobilele.models.entity;

import com.example.mobilele.models.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "roles")
public class UserRole extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role name;
}
