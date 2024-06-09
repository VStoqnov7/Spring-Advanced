package com.example.mobilele.service.impl;

import com.example.mobilele.models.dto.OfferAddDTO;
import com.example.mobilele.models.entity.Brand;
import com.example.mobilele.models.entity.Model;
import com.example.mobilele.models.entity.Offer;
import com.example.mobilele.models.entity.User;
import com.example.mobilele.repository.OfferRepository;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:00");

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, UserService userService) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void saveOffer(OfferAddDTO offerAddDTO) {
        Offer offer = modelMapper.map(offerAddDTO, Offer.class);
        Brand brand = new Brand()
                .setName(offerAddDTO.getBrand())
                .setCreated(LocalDateTime.now());
        Model model = new Model()
                .setBrand(brand)
                .setName(offerAddDTO.getModel())
                .setCreated(LocalDateTime.now())
                .setImageUrl(offer.getImageUrl())
                .setCategory(offerAddDTO.getCategory());
        offer.setModel(model);
        offer.setCreated(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
        User user = this.userService.findCurrendUser();
        offer.setSeller(user);
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
            Brand brand = new Brand()
                    .setName(offerAddDTO.getBrand())
                    .setCreated(LocalDateTime.now());
            Model model = new Model()
                    .setBrand(brand)
                    .setName(offerAddDTO.getModel())
                    .setCreated(LocalDateTime.now())
                    .setImageUrl(offer.getImageUrl())
                    .setCategory(offerAddDTO.getCategory());
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
