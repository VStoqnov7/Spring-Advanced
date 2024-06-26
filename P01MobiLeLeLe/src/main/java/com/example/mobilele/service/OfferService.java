package com.example.mobilele.service;

import com.example.mobilele.models.dto.OfferAddDTO;
import com.example.mobilele.models.entity.Offer;
import com.example.mobilele.user.MobileleleUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OfferService {

    Page<Offer> getAllOffers(Pageable pageable);

    Offer findOfferById(String offerId);

    void updateOffer(String offerId, OfferAddDTO offerAddDTO);

    void deleteOffer(String offerId);

    void saveOffer(OfferAddDTO offerAddDTO, MobileleleUserDetails userDetails);

}
