package com.example.mobilele.models.entity;

import com.example.mobilele.models.enums.Engine;
import com.example.mobilele.models.enums.Transmission;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity{

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Engine engine;

    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private int mileage;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Transmission transmission;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private LocalDateTime created;

    @Column
    private LocalDateTime modified;

    @ManyToOne(cascade = CascadeType.ALL)
    private Model model;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User seller;
}
