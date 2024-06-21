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
    public void testSaveOffer() {
        // Given
        when(brandRepository.saveAndFlush(Mockito.any(Brand.class)))
                .thenReturn(brand);
        when(userService.findByUsername(userDetails.getUsername())).thenReturn(Optional.of(user));
        when(modelMapper.map(offerAddDTO, Offer.class)).thenReturn(offer);

        // When
        offerService.saveOffer(offerAddDTO, userDetails);

        // Then
        verify(offerRepository).saveAndFlush(offerCaptor.capture());
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
        List<Offer> offers = new ArrayList<>();
        offers.add(offer);
        when(offerRepository.findAll()).thenReturn(offers);

        List<Offer> result = offerService.getAllOffers();

        assertEquals(1, result.size());
        assertEquals(offer, result.get(0));
    }

    @Test
    void testFindOfferById() {
        when(offerRepository.findById("1")).thenReturn(Optional.of(offer));
        Offer result = offerService.findOfferById("1");
        assertNotNull(result);
        assertEquals(offer, result);
    }

}