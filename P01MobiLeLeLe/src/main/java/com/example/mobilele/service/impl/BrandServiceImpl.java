package com.example.mobilele.service.impl;

import com.example.mobilele.models.entity.Brand;
import com.example.mobilele.repository.BrandRepository;
import com.example.mobilele.service.BrandService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    @Cacheable(value = "brands")
    public List<Brand> findAll() {
        return this.brandRepository.findAllBrands();
    }
}


