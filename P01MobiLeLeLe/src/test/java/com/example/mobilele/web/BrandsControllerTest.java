package com.example.mobilele.web;

import com.example.mobilele.models.entity.Brand;
import com.example.mobilele.repository.BrandRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class BrandsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    @Transactional
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowAllBrandsForm() throws Exception {
        Brand brand1 = new Brand().setName("Brand1").setCreated(LocalDateTime.now());
        Brand brand2 = new Brand().setName("Brand2").setCreated(LocalDateTime.now());

        this.brandRepository.save(brand1);
        this.brandRepository.save(brand2);

        mockMvc.perform(get("/user/brands/all")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("brands"))
                .andExpect(model().attributeExists("allBrands"))
                .andExpect(model().attribute("allBrands", hasSize(2)))
                .andExpect(model().attribute("allBrands", containsInAnyOrder(
                        hasProperty("name", is("Brand1")),
                        hasProperty("name", is("Brand2"))
                )));
    }
}