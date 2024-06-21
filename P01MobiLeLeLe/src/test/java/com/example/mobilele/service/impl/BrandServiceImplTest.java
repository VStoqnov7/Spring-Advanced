package com.example.mobilele.service.impl;

import com.example.mobilele.models.entity.Brand;
import com.example.mobilele.repository.BrandRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandServiceImplTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Test
    public void testFindAllBrands() {
        // Given
        List<Brand> mockBrands = new ArrayList<>();
        mockBrands.add(new Brand().setName("Toyota"));
        mockBrands.add(new Brand().setName("Honda"));
        when(brandRepository.findAllBrands()).thenReturn(mockBrands);

        // When
        List<Brand> result = brandService.findAll();

        // Then
        assertEquals(2, result.size());
        assertEquals("Toyota", result.get(0).getName());
        assertEquals("Honda", result.get(1).getName());
    }
}