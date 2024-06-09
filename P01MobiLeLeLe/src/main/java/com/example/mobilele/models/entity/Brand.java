package com.example.mobilele.models.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name = "brands")
public class Brand extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column
    private LocalDateTime modified;

    @OneToMany(mappedBy = "brand")
    private List<Model> models;
}
