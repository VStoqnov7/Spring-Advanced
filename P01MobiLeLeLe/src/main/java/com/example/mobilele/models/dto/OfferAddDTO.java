package com.example.mobilele.models.dto;

import com.example.mobilele.models.enums.Category;
import com.example.mobilele.models.enums.Engine;
import com.example.mobilele.models.enums.Transmission;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class OfferAddDTO {

    @NotBlank
    private String model;

    @PositiveOrZero
    private BigDecimal price;

    @NotNull
    private Engine engine;

    @NotNull
    private Transmission transmission;

    @Min(1900)
    private int year;

    @Min(1)
    private int mileage;

    @NotBlank
    private String imageUrl;

    @Size(min = 3)
    private String description;

    @NotNull
    private Category category;

    @NotBlank
    private String brand;
}
