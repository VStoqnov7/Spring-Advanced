package com.example.mobilele.models.dto;

import com.example.mobilele.models.enums.Category;
import com.example.mobilele.models.enums.Engine;
import com.example.mobilele.models.enums.Transmission;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class OfferAddDTO {

    @NotNull
    @NotBlank
    private String model;

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    @NotNull
    private Engine engine;

    @NotNull
    private Transmission transmission;

    @NotNull
    @Min(1900)
    private int year;

    @NotNull
    @Min(1)
    private int mileage;

    @NotNull
    @NotBlank
    private String imageUrl;

    @NotNull
    @Size(min = 3)
    private String description;

    @NotNull
    private Category category;

    @NotNull
    @NotBlank
    private String brand;
}
