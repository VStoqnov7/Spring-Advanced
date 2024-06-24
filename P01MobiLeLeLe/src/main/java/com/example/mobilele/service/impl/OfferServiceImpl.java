package com.example.mobilele.service.impl;

import com.example.mobilele.models.dto.OfferAddDTO;
import com.example.mobilele.models.entity.Brand;
import com.example.mobilele.models.entity.Model;
import com.example.mobilele.models.entity.Offer;
import com.example.mobilele.models.entity.User;
import com.example.mobilele.repository.BrandRepository;
import com.example.mobilele.repository.ModelRepository;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.UserService;
import com.example.mobilele.user.MobileleleUserDetails;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, UserService userService, BrandRepository brandRepository, ModelRepository modelRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    @CacheEvict(value = {"offers", "brands"}, allEntries = true, beforeInvocation = true)
    public void saveOffer(OfferAddDTO offerAddDTO, MobileleleUserDetails userDetails) {
        Offer offer = modelMapper.map(offerAddDTO, Offer.class);

        Brand existBrand = this.brandRepository.findByName(offerAddDTO.getBrand());
        if (existBrand == null) {
            Brand brand = new Brand()
                    .setName(offerAddDTO.getBrand())
                    .setCreated(LocalDateTime.now())
                    .setModels(new ArrayList<>());
            existBrand = brand;
        }
        Model model = new Model()
                .setBrand(existBrand)
                .setName(offerAddDTO.getModel())
                .setCreated(LocalDateTime.now())
                .setImageUrl(offer.getImageUrl())
                .setCategory(offerAddDTO.getCategory());
        existBrand.getModels().add(model);

        User user = this.userService.findByUsername(userDetails.getUsername()).get();

        offer.setSeller(user);
        offer.setModel(model);
        offer.setCreated(LocalDateTime.now());
        this.offerRepository.save(offer);
    }
    @Override
    @Cacheable(value = "offers")
    public List<Offer> getAllOffers() {
        return this.offerRepository.findAll();
    }

    @Override
    public Offer findOfferById(String offerId) {
        return this.offerRepository.findById(offerId).orElse(null);
    }

    @Override
    @CacheEvict(value = {"offers", "brands"}, allEntries = true, beforeInvocation = true)
    public void updateOffer(String offerId, OfferAddDTO offerAddDTO) {
        Offer offer = this.offerRepository.findById(offerId).orElse(null);
        if (offer != null) {
            Model model = offer.getModel();
            Brand brand = model.getBrand();

            model.setName(offerAddDTO.getModel());
            model.setImageUrl(offerAddDTO.getImageUrl());
            model.setCategory(offerAddDTO.getCategory());
            model.setCreated(LocalDateTime.now());


            if (!brand.getName().equals(offerAddDTO.getBrand())) {
                brand = this.brandRepository.findByName(offerAddDTO.getBrand());
                if (brand == null) {
                    brand = new Brand();
                    brand.setName(offerAddDTO.getBrand());
                    brand.setCreated(LocalDateTime.now());
                    brand = this.brandRepository.saveAndFlush(brand);
                }
                model.setBrand(brand);
            }

            offer.setEngine(offerAddDTO.getEngine());
            offer.setTransmission(offerAddDTO.getTransmission());
            offer.setYear(offerAddDTO.getYear());
            offer.setMileage(offerAddDTO.getMileage());
            offer.setDescription(offerAddDTO.getDescription());
            offer.setImageUrl(offerAddDTO.getImageUrl());
            offer.setPrice(offerAddDTO.getPrice());
            offer.setCreated(LocalDateTime.now());

            this.modelRepository.saveAndFlush(model);
            this.offerRepository.saveAndFlush(offer);
//            applicationEventPublisher.publishEvent(new OfferModifiedEvent(this, offerId, "updated"));
        }
    }

    @Override
    @CacheEvict(value = {"offers", "brands"}, allEntries = true, beforeInvocation = true)
    public void deleteOffer(String offerId) {
        this.offerRepository.deleteById(offerId);
//        applicationEventPublisher.publishEvent(new OfferModifiedEvent(this, offerId, "deleted"));
    }

}
