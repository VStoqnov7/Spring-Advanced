package com.example.mobilele.service;

import com.example.mobilele.models.dto.OfferAddDTO;
import com.example.mobilele.models.entity.Offer;
import com.example.mobilele.user.MobileleleUserDetails;

import java.util.List;

public interface OfferService {

    List<Offer> allOffers();

    Offer findOfferById(String offerId);

    void updateOffer(String offerId, OfferAddDTO offerAddDTO);

    void deleteOffer(String offerId);

    void saveOffer(OfferAddDTO offerAddDTO, MobileleleUserDetails userDetails);

    List<Offer> getAllOffers();

}
