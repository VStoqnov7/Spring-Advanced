package com.example.mobilele.service.impl;

import com.example.mobilele.models.dto.OfferAddDTO;
import com.example.mobilele.models.entity.Brand;
import com.example.mobilele.models.entity.Model;
import com.example.mobilele.models.entity.Offer;
import com.example.mobilele.models.entity.User;
import com.example.mobilele.repository.BrandRepository;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final BrandRepository brandRepository;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00");

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, UserService userService, BrandRepository brandRepository) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.brandRepository = brandRepository;
    }

    @Override
    public void saveOffer(OfferAddDTO offerAddDTO) {
        Offer offer = modelMapper.map(offerAddDTO, Offer.class);

        Brand existBrand = this.brandRepository.findByName(offerAddDTO.getBrand());
        if (existBrand == null) {
            Brand brand = new Brand()
                    .setName(offerAddDTO.getBrand())
                    .setCreated(LocalDateTime.now())
                    .setModels(new ArrayList<>());
            existBrand = this.brandRepository.saveAndFlush(brand);
        }
        Model model = new Model()
                .setBrand(existBrand)
                .setName(offerAddDTO.getModel())
                .setCreated(LocalDateTime.now())
                .setImageUrl(offer.getImageUrl())
                .setCategory(offerAddDTO.getCategory());
        existBrand.getModels().add(model);

        offer.setModel(model);
        offer.setCreated(LocalDateTime.now());
        this.offerRepository.saveAndFlush(offer);
    }

    @Override
    public List<Offer> allOffers() {
        return this.offerRepository.findAll();
    }

    @Override
    public Offer findOfferById(String offerId) {
        return this.offerRepository.findById(offerId).orElse(null);
    }

    @Override
    public void updateOffer(String offerId, OfferAddDTO offerAddDTO) {
        Offer offer = this.offerRepository.findById(offerId).orElse(null);
        if (offer != null) {
            Model model = new Model()
                    .setName(offerAddDTO.getModel())
                    .setCreated(LocalDateTime.now())
                    .setImageUrl(offer.getImageUrl())
                    .setCategory(offerAddDTO.getCategory());
            Brand existBrand = this.brandRepository.findByName(offerAddDTO.getBrand());
            if (existBrand == null) {
                Brand brand = new Brand()
                        .setName(offerAddDTO.getBrand())
                        .setCreated(LocalDateTime.now());
                existBrand = this.brandRepository.saveAndFlush(brand);
            }
            existBrand.getModels().add(model);

            offer.setModel(model);
            offer.setCreated(LocalDateTime.now());
            this.offerRepository.saveAndFlush(offer);
        }
    }

    @Override
    public void deleteOffer(String offerId) {
        this.offerRepository.deleteById(offerId);
    }
}
