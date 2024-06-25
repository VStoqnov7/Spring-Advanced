package com.example.mobilele.web;

import com.example.mobilele.models.dto.OfferAddDTO;
import com.example.mobilele.models.dto.UserRegistrationDTO;
import com.example.mobilele.models.entity.Brand;
import com.example.mobilele.models.entity.Model;
import com.example.mobilele.models.entity.Offer;
import com.example.mobilele.models.entity.User;
import com.example.mobilele.models.enums.Category;
import com.example.mobilele.models.enums.Engine;
import com.example.mobilele.models.enums.Role;
import com.example.mobilele.models.enums.Transmission;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.user.MobileleleUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @MockBean
    private OfferService offerService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testAddOfferPage() throws Exception {
        mockMvc.perform(get("/user/offers/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("offer-add"))
                .andExpect(model().attributeExists("offerAddDTO"))
                .andExpect(model().attribute("engines", Engine.values()))
                .andExpect(model().attribute("transmissions", Transmission.values()))
                .andExpect(model().attribute("categories", Category.values()));
    }


    @Test
    @Transactional
    @WithMockUser(username = "user", roles = {"USER"})
    void testAddOfferValid() throws Exception {
        MobileleleUserDetails userDetails = new MobileleleUserDetails(
                "1",
                "user",
                "202020",
                "Georgi",
                "Petrov",
                Collections.singletonList(new SimpleGrantedAuthority(Role.USER.name())));

        UserRegistrationDTO user = new UserRegistrationDTO()
                .setFirstName("Georgi")
                .setLastName("Petrov")
                .setUsername("user")
                .setPassword("202020");

        this.userService.saveUser(user);

        mockMvc.perform(post("/user/offers/add")
                        .param("model", "X5")
                        .param("price", "30000.00")
                        .param("engine", "DIESEL")
                        .param("transmission", "MANUAL")
                        .param("year", "2020")
                        .param("mileage", "50000")
                        .param("imageUrl", "example.com/image")
                        .param("description", "Test offer")
                        .param("category", "CAR")
                        .param("brand", "BMW")
                        .with(csrf())
                .with(user(userDetails)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "user", roles = {"USER"})
    void testAddOfferFailure() throws Exception {
        MobileleleUserDetails userDetails = new MobileleleUserDetails(
                "1",
                "user",
                "202020",
                "Georgi",
                "Petrov",
                Collections.singletonList(new SimpleGrantedAuthority(Role.USER.name())));

        UserRegistrationDTO user = new UserRegistrationDTO()
                .setFirstName("Georgi")
                .setLastName("Petrov")
                .setUsername("user")
                .setPassword("202020");

        this.userService.saveUser(user);

        mockMvc.perform(post("/user/offers/add")
                        .param("model", "")
                        .param("price", "-30000.00")
                        .param("engine", "")
                        .param("transmission", "")
                        .param("year", "1899")
                        .param("mileage", "0")
                        .param("imageUrl", "")
                        .param("description", "Te")
                        .param("category", "")
                        .param("brand", "")
                        .with(csrf())
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(view().name("offer-add"))
                .andExpect(model().attributeHasFieldErrors("offerAddDTO",
                        "model", "price", "engine", "transmission", "year", "mileage", "imageUrl", "description", "category", "brand"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testAllOffers() throws Exception {

        Offer offer1 = new Offer()
                .setModel(new Model().setBrand(new Brand().setName("Toyota")))
                .setImageUrl("Test1")
                .setMileage(1200)
                .setPrice(BigDecimal.valueOf(2000))
                .setEngine(Engine.DIESEL)
                .setTransmission(Transmission.MANUAL);
        Offer offer2 = new Offer()
                .setModel(new Model().setBrand(new Brand().setName("BMW")))
                .setImageUrl("Test1")
                .setMileage(1300)
                .setPrice(BigDecimal.valueOf(3000))
                .setEngine(Engine.GASOLINE)
                .setTransmission(Transmission.AUTOMATIC);
        List<Offer> allOffers = Arrays.asList(offer1, offer2);

        when(offerService.getAllOffers()).thenReturn(allOffers);

        mockMvc.perform(get("/user/offers/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("offers"))
                .andExpect(model().attributeExists("allOffers"))
                .andExpect(model().attribute("allOffers", hasSize(2)))
                .andExpect(model().attribute("allOffers", containsInAnyOrder(
                        hasProperty("model", hasProperty("brand", hasProperty("name", is("Toyota")))),
                        hasProperty("model", hasProperty("brand", hasProperty("name", is("BMW"))))
                )));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testOfferDetails() throws Exception {
        Offer offer = new Offer()
                .setModel(new Model().setBrand(new Brand().setName("Toyota")))
                .setDescription("Test1")
                .setImageUrl("Test1")
                .setMileage(1200)
                .setPrice(BigDecimal.valueOf(2000))
                .setEngine(Engine.DIESEL)
                .setTransmission(Transmission.MANUAL)
                .setCreated(LocalDateTime.now())
                .setSeller(new User().setFirstName("John").setLastName("Doe"));

        when(offerService.findOfferById("1")).thenReturn(offer);

        mockMvc.perform(get("/user/offers/details/{offerId}", "1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("details"))
                .andExpect(model().attributeExists("currentOffer"))
                .andExpect(model().attribute("currentOffer", hasProperty("model", hasProperty("brand", hasProperty("name", is("Toyota"))))))
                .andExpect(model().attribute("currentOffer", hasProperty("description", is("Test1"))))
                .andExpect(model().attribute("currentOffer", hasProperty("engine", is(Engine.DIESEL))))
                .andExpect(model().attribute("currentOffer", hasProperty("transmission", is(Transmission.MANUAL))))
                .andExpect(model().attribute("currentOffer", hasProperty("mileage", is(1200))))
                .andExpect(model().attribute("currentOffer", hasProperty("price", is(BigDecimal.valueOf(2000)))))
                .andExpect(model().attribute("currentOffer", hasProperty("imageUrl", is("Test1"))))
                .andExpect(model().attribute("currentOffer", hasProperty("created", notNullValue())))
                .andExpect(model().attribute("currentOffer", hasProperty("seller", hasProperty("firstName", is("John")))))
                .andExpect(model().attribute("currentOffer", hasProperty("seller", hasProperty("lastName", is("Doe")))));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testUpdateOfferDetails() throws Exception {
        // Create a sample offer for testing
        Offer offer = new Offer()
                .setModel(new Model().setBrand(new Brand().setName("Toyota")).setName("Camry").setCategory(Category.CAR))
                .setDescription("Test1")
                .setImageUrl("Test1")
                .setMileage(1200)
                .setPrice(BigDecimal.valueOf(2000))
                .setEngine(Engine.DIESEL)
                .setTransmission(Transmission.MANUAL)
                .setYear(2023);

        when(offerService.findOfferById("1")).thenReturn(offer);

        mockMvc.perform(get("/user/offers/details/update/{offerId}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("update"))
                .andExpect(model().attributeExists("currentOffer")) // Ensure "currentOffer" attribute exists
                .andExpect(model().attribute("currentOffer", hasProperty("model", hasProperty("brand", hasProperty("name", is("Toyota"))))))
                .andExpect(model().attribute("currentOffer", hasProperty("description", is("Test1"))))
                .andExpect(model().attribute("currentOffer", hasProperty("engine", is(Engine.DIESEL))))
                .andExpect(model().attribute("currentOffer", hasProperty("transmission", is(Transmission.MANUAL))))
                .andExpect(model().attribute("currentOffer", hasProperty("mileage", is(1200))))
                .andExpect(model().attribute("currentOffer", hasProperty("price", is(BigDecimal.valueOf(2000)))))
                .andExpect(model().attribute("currentOffer", hasProperty("imageUrl", is("Test1"))))
                .andExpect(model().attribute("currentOffer", hasProperty("year", is(2023))))
                .andExpect(model().attribute("currentOffer", hasProperty("model", hasProperty("name", is("Camry")))))
                .andExpect(model().attribute("currentOffer", hasProperty("model", hasProperty("category", is(Category.CAR)))))
                .andExpect(model().attributeExists("engines"))
                .andExpect(model().attributeExists("transmissions"))
                .andExpect(model().attributeExists("categories"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testUpdateOfferSuccess() throws Exception {
        mockMvc.perform(post("/user/offers/details/update/{offerId}", "1")
                        .param("model", "X3")
                        .param("price", "2000")
                        .param("engine", Engine.DIESEL.toString())
                        .param("transmission", Transmission.MANUAL.toString())
                        .param("year", "2022")
                        .param("mileage", "2000")
                        .param("imageUrl", "Test1")
                        .param("description", "Test2")
                        .param("category", Category.CAR.toString())
                        .param("brand", "BMW")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/offers/details/1"));

        verify(offerService, times(1)).updateOffer(eq("1"), any(OfferAddDTO.class));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testUpdateOfferValidationErrors() throws Exception {

        Offer mockOffer = new Offer();
        when(offerService.findOfferById("1")).thenReturn(mockOffer);
        mockMvc.perform(post("/user/offers/details/update/{offerId}", "1")
                        .param("id","1")
                        .param("model", "")
                        .param("price", "-30000.00")
                        .param("engine", "")
                        .param("transmission", "")
                        .param("year", "1899")
                        .param("mileage", "0")
                        .param("imageUrl", "")
                        .param("description", "Te")
                        .param("category", "")
                        .param("brand", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("update"))
                .andExpect(model().attributeHasFieldErrors("offerAddDTO",
                        "model", "price", "engine", "transmission", "year", "mileage", "imageUrl", "description", "category", "brand"));

        // Verify that offerService.updateOffer was not called
        verify(offerService, times(0)).updateOffer(any(String.class), any(OfferAddDTO.class));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN"})
    void testDeleteOfferSuccess() throws Exception {
        String offerId = "1";

        doNothing().when(offerService).deleteOffer(offerId);

        mockMvc.perform(get("/user/offers/details/delete/{offerId}", offerId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/offers/all"))
                .andExpect(view().name("redirect:/user/offers/all"));

        verify(offerService, times(1)).deleteOffer(offerId);
    }
}