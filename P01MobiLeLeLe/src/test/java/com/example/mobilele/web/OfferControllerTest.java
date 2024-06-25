package com.example.mobilele.web;

import com.example.mobilele.models.dto.UserRegistrationDTO;
import com.example.mobilele.models.enums.Category;
import com.example.mobilele.models.enums.Engine;
import com.example.mobilele.models.enums.Role;
import com.example.mobilele.models.enums.Transmission;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.service.UserService;
import com.example.mobilele.user.MobileleleUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private OfferRepository offerRepository;

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
}