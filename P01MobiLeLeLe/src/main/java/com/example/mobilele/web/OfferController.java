package com.example.mobilele.web;

import com.example.mobilele.models.dto.OfferAddDTO;
import com.example.mobilele.models.entity.Offer;
import com.example.mobilele.models.enums.Category;
import com.example.mobilele.models.enums.Engine;
import com.example.mobilele.models.enums.Transmission;
import com.example.mobilele.service.OfferService;
import com.example.mobilele.user.MobileleleUserDetails;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @ModelAttribute(name = "offerAddDTO")
    public OfferAddDTO offerAddDTO(){
        OfferAddDTO offerAddDTO = new OfferAddDTO();
        offerAddDTO.setEngine(Engine.GASOLINE);
        offerAddDTO.setTransmission(Transmission.MANUAL);
        offerAddDTO.setCategory(Category.CAR);
        return offerAddDTO;
    }


    @GetMapping("/add")
    public ModelAndView addOffer(ModelAndView model){
        model.addObject("engines", Engine.values());
        model.addObject("transmissions", Transmission.values());
        model.addObject("categories", Category.values());
        model.setViewName("offer-add");
        return model;
    }

    @PostMapping("/add")
    public ModelAndView addOffers(ModelAndView model,
                                  @Valid OfferAddDTO offerAddDTO,
                                  BindingResult bindingResult,
                                  @AuthenticationPrincipal MobileleleUserDetails userDetails){
        if (bindingResult.hasErrors()){
            model.addObject("engines", Engine.values());
            model.addObject("transmissions", Transmission.values());
            model.addObject("categories", Category.values());
            model.setViewName("offer-add");
            return model;
        }
        this.offerService.saveOffer(offerAddDTO,userDetails);
        model.setViewName("redirect:/home");
        return model;
    }

    @GetMapping("/all")
    public ModelAndView getAllOffers(@PageableDefault(sort = "id", size = 5) Pageable pageable, ModelAndView model) {
        Page<Offer> allOffersPage = offerService.getAllOffers(pageable);
        model.setViewName("offers");
        model.addObject("offers", allOffersPage);
        return model;
    }


    @GetMapping("/details/{offerId}")
    public ModelAndView offerDetails(ModelAndView model, @PathVariable String offerId){
        Offer offer = this.offerService.findOfferById(offerId);
        if (offer != null){
            model.addObject("currentOffer", offer);
        }
        model.setViewName("details");
        return model;
    }

    @GetMapping("/details/update/{offerId}")
    public ModelAndView updateOffer(@ModelAttribute OfferAddDTO offerAddDTO, ModelAndView model, @PathVariable String offerId){
        Offer offer = this.offerService.findOfferById(offerId);
        if (offer != null){
            offerAddDTO.setModel(offer.getModel().getName())
                    .setPrice(offer.getPrice())
                    .setEngine(offer.getEngine())
                    .setTransmission(offer.getTransmission())
                    .setYear(offer.getYear())
                    .setMileage(offer.getMileage())
                    .setImageUrl(offer.getImageUrl())
                    .setDescription(offer.getDescription())
                    .setCategory(offer.getModel().getCategory())
                    .setBrand(offer.getModel().getBrand().getName());
            model.addObject("currentOffer", offer);
            model.addObject("engines", Engine.values());
            model.addObject("transmissions", Transmission.values());
            model.addObject("categories", Category.values());
        }
        model.setViewName("update");
        return model;
    }

    @PostMapping("/details/update/{offerId}")
    public ModelAndView updateOffer(ModelAndView model,
                                    @PathVariable String offerId,
                                    @Valid OfferAddDTO offerAddDTO,
                                    BindingResult bindingResult){
        Offer offer = this.offerService.findOfferById(offerId);
        if (bindingResult.hasErrors()){
            model.addObject("currentOffer", offer);
            model.addObject("engines", Engine.values());
            model.addObject("transmissions", Transmission.values());
            model.addObject("categories", Category.values());
            model.addObject("offerId", offerId);
            model.setViewName("update");
            return model;
        }
        this.offerService.updateOffer(offerId,offerAddDTO);
        model.setViewName("redirect:/user/offers/details/" + offerId);
        return model;
    }
    @GetMapping("/details/delete/{offerId}")
    public ModelAndView deleteOffer(@PathVariable String offerId,ModelAndView model){
        this.offerService.deleteOffer(offerId);
        model.setViewName("redirect:/user/offers/all");
        return model;
    }
}
