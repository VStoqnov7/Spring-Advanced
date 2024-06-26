package com.example.mobilele.service.impl;

import com.example.mobilele.models.dto.OfferAddDTO;
import com.example.mobilele.models.entity.*;
import com.example.mobilele.models.enums.Category;
import com.example.mobilele.models.enums.Engine;
import com.example.mobilele.models.enums.Role;
import com.example.mobilele.models.enums.Transmission;
import com.example.mobilele.repository.BrandRepository;
import com.example.mobilele.repository.ModelRepository;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.service.UserService;
import com.example.mobilele.user.MobileleleUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ModelRepository modelRepository;

    @InjectMocks
    private OfferServiceImpl offerService;

    @Captor
    private ArgumentCaptor<Brand> brandCaptor;
    @Captor
    private ArgumentCaptor<Offer> offerCaptor;

    private OfferAddDTO offerAddDTO;
    private MobileleleUserDetails userDetails;
    private Offer offer;
    private Brand brand;
    private Model model;
    private User user;


    @BeforeEach
    void setUp() {

        user = new User()
                .setUsername("Gosho777")
                .setPassword("202020")
                .setFirstName("Georgi")
                .setLastName("Petrov")
                .setActive(true)
                .setRoles(List.of(new UserRole().setName(Role.USER)))
                .setImageUrl("example.com/image")
                .setCreated(LocalDateTime.now())
                .setModified(LocalDateTime.now());

        brand = new Brand()
                .setName("BMW")
                .setCreated(LocalDateTime.now())
                .setModified(LocalDateTime.now());

        model = new Model()
                .setName("X5")
                .setCategory(Category.CAR)
                .setImageUrl("example.com/image")
                .setStartYear(2000)
                .setEndYear(2010)
                .setCreated(LocalDateTime.now())
                .setModified(LocalDateTime.now())
                .setBrand(brand);

        offerAddDTO = new OfferAddDTO()
                .setModel("X5")
                .setPrice(BigDecimal.valueOf(30000.00))
                .setEngine(Engine.DIESEL)
                .setTransmission(Transmission.MANUAL)
                .setYear(2020)
                .setMileage(50000)
                .setImageUrl("example.com/image")
                .setDescription("Test offer")
                .setCategory(Category.CAR)
                .setBrand("BMW");

        offer = new Offer()
                .setDescription("Test offer")
                .setEngine(Engine.DIESEL)
                .setImageUrl("example.com/image")
                .setMileage(50000)
                .setPrice(BigDecimal.valueOf(30000.00))
                .setTransmission(Transmission.MANUAL)
                .setYear(2020)
                .setCreated(LocalDateTime.now())
                .setModified(LocalDateTime.now())
                .setModel(model)
                .setSeller(user);

        userDetails = new MobileleleUserDetails("1", "testUser", "test",
                "Georgi", "Petrov", null);
    }

    @Test
    @Transactional
    public void testSaveOffer() {
        // Given
        when(userService.findByUsername(userDetails.getUsername())).thenReturn(Optional.of(user));
        when(modelMapper.map(offerAddDTO, Offer.class)).thenReturn(offer);

        // When
        offerService.saveOffer(offerAddDTO, userDetails);

        // Then
        verify(offerRepository).save(offerCaptor.capture());
        Offer savedOffer = offerCaptor.getValue();

        assertEquals(offer.getModel().getName(), savedOffer.getModel().getName());
        assertEquals(offer.getSeller().getUsername(), savedOffer.getSeller().getUsername());

        assertEquals(offer.getDescription(), savedOffer.getDescription());
        assertEquals(offer.getEngine(), savedOffer.getEngine());
        assertEquals(offer.getTransmission(), savedOffer.getTransmission());
        assertEquals(offer.getYear(), savedOffer.getYear());
        assertEquals(offer.getMileage(), savedOffer.getMileage());
        assertEquals(offer.getImageUrl(), savedOffer.getImageUrl());
        assertEquals(offer.getPrice(), savedOffer.getPrice());
        assertEquals(offer.getModel().getName(), savedOffer.getModel().getName());
        assertEquals(offer.getModel().getBrand().getName(), savedOffer.getModel().getBrand().getName());
        assertEquals(offer.getSeller().getUsername(), savedOffer.getSeller().getUsername());
    }


    @Test
    void testGetAllOffers() {
        // Given
        List<Offer> offers = new ArrayList<>();
        offers.add(offer);

        Pageable pageable = PageRequest.of(0, 5);
        Page<Offer> page = new PageImpl<>(offers, pageable, offers.size());

        when(offerRepository.findAll(pageable)).thenReturn(page);

        // When
        Page<Offer> result = offerService.getAllOffers(pageable);

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(offer, result.getContent().get(0));
    }

    @Test
    void testFindOfferById() {
        // Given
        when(offerRepository.findById("1")).thenReturn(Optional.of(offer));
        // When
        Offer result = offerService.findOfferById("1");
        // Then
        assertNotNull(result);
        assertEquals(offer, result);
    }

    @Test
    void testUpdateOfferWithExistingBrand() {
        // Given
        // Стойности за новото оферта
        OfferAddDTO newOfferAddDTO = new OfferAddDTO()
                .setModel("X9")
                .setPrice(BigDecimal.valueOf(10000.00))
                .setEngine(Engine.GASOLINE)
                .setTransmission(Transmission.AUTOMATIC)
                .setYear(2010)
                .setMileage(10000)
                .setImageUrl("example.com/image")
                .setDescription("Test offer")
                .setCategory(Category.CAR)
                .setBrand("BMW");

        // Мокиране на връщането на старата оферта
        when(offerRepository.findById("1")).thenReturn(Optional.of(offer));

        // When
        // Извикване на метода updateOffer
        offerService.updateOffer("1", newOfferAddDTO);

        // Улавяне на актуализираната оферта
        verify(offerRepository).saveAndFlush(offerCaptor.capture());
        Offer updatedOffer = offerCaptor.getValue();

        // Then
        // Проверка на стойностите след първото актуализиране
        assertEquals(newOfferAddDTO.getModel(), updatedOffer.getModel().getName());
        assertEquals(newOfferAddDTO.getPrice(), updatedOffer.getPrice());
        assertEquals(newOfferAddDTO.getEngine(), updatedOffer.getEngine());
        assertEquals(newOfferAddDTO.getTransmission(), updatedOffer.getTransmission());
        assertEquals(newOfferAddDTO.getYear(), updatedOffer.getYear());
        assertEquals(newOfferAddDTO.getMileage(), updatedOffer.getMileage());
        assertEquals(newOfferAddDTO.getImageUrl(), updatedOffer.getImageUrl());
        assertEquals(newOfferAddDTO.getDescription(), updatedOffer.getDescription());
        assertEquals(newOfferAddDTO.getCategory(), updatedOffer.getModel().getCategory());
        assertEquals(newOfferAddDTO.getBrand(), updatedOffer.getModel().getBrand().getName());
    }

    @Test
    void testUpdateOfferWithBrandChange() {
        // Given
        OfferAddDTO newOfferAddDTO = new OfferAddDTO()
                .setModel("X9")
                .setPrice(BigDecimal.valueOf(10000.00))
                .setEngine(Engine.GASOLINE)
                .setTransmission(Transmission.AUTOMATIC)
                .setYear(2010)
                .setMileage(10000)
                .setImageUrl("example.com/image")
                .setDescription("Test offer")
                .setCategory(Category.CAR)
                .setBrand("Toyota");

        when(offerRepository.findById("1")).thenReturn(Optional.of(offer));
        when(brandRepository.findByName("Toyota")).thenReturn(null);
        when(brandRepository.saveAndFlush(Mockito.any(Brand.class)))
                .thenAnswer(invocation -> {
                    Brand savedBrand = invocation.getArgument(0);
                    savedBrand.setName("Toyota");
                    return savedBrand;
                });

        // When
        offerService.updateOffer("1", newOfferAddDTO);

        // Then
        verify(offerRepository).saveAndFlush(offerCaptor.capture());
        Offer updatedOffer = offerCaptor.getValue();
        assertEquals(newOfferAddDTO.getModel(), updatedOffer.getModel().getName());
        assertEquals(newOfferAddDTO.getPrice(), updatedOffer.getPrice());
        assertEquals(newOfferAddDTO.getEngine(), updatedOffer.getEngine());
        assertEquals(newOfferAddDTO.getTransmission(), updatedOffer.getTransmission());
        assertEquals(newOfferAddDTO.getYear(), updatedOffer.getYear());
        assertEquals(newOfferAddDTO.getMileage(), updatedOffer.getMileage());
        assertEquals(newOfferAddDTO.getImageUrl(), updatedOffer.getImageUrl());
        assertEquals(newOfferAddDTO.getDescription(), updatedOffer.getDescription());
        assertEquals(newOfferAddDTO.getCategory(), updatedOffer.getModel().getCategory());
        assertEquals(newOfferAddDTO.getBrand(), updatedOffer.getModel().getBrand().getName());
    }

    @Test
    void testDeleteOffer() {
        offerService.deleteOffer("1");
        verify(offerRepository).deleteById("1");
    }
}