package com.example.mobilele.web;

import com.example.mobilele.models.entity.Brand;
import com.example.mobilele.service.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class BrandsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testShowAllBrandsForm() throws Exception {
        Brand brand1 = new Brand().setName("Brand1");
        Brand brand2 = new Brand().setName("Brand2");
        List<Brand> allBrands = Arrays.asList(brand1, brand2);

        when(brandService.findAll()).thenReturn(allBrands);

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