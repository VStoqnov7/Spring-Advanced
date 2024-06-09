package com.example.mobilele.service;

import com.example.mobilele.models.dto.OfferAddDTO;
import com.example.mobilele.models.entity.Offer;

import java.util.List;

public interface OfferService {

    void saveOffer(OfferAddDTO offerAddDTO);

    List<Offer> allOffers();


    Offer findOfferById(String offerId);

    void updateOffer(String offerId, OfferAddDTO offerAddDTO);

    void deleteOffer(String offerId);
}
