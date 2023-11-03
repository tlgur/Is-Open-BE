package com.tlgur.isOpen.controller.place;

import com.tlgur.isOpen.domain.place.Restaurant;
import com.tlgur.isOpen.dto.*;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.FoodType;
import com.tlgur.isOpen.domain.enums.PlaceType;
import com.tlgur.isOpen.error.exceptions.BadPlaceIDException;
import com.tlgur.isOpen.repository.place.RestaurantRepository;
import com.tlgur.isOpen.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final PlaceService placeService;
    private final RestaurantRepository restaurantRepository;
    private static final PlaceType placeType = PlaceType.RESTAURANT;

    //usage list 조회
    @GetMapping("/place/usages/restaurant")
    public RestaurantUsageInformationsContainer getUsageInformation() throws ClassNotFoundException {
        return new RestaurantUsageInformationsContainer();
    }

    //Place Type : Restaurant
    //All Place Type Card List 조회 By & Campus & 세부 카테고리
    @GetMapping("/place/cards/restaurant")
    public SliceDTO<PlaceCard> getPlaceCardsOfRestaurant(@RequestParam Campus campus,
                                                         @RequestParam Optional<FoodType> foodType,
                                                         @RequestParam Optional<Boolean> hasDeliveryService,
                                                         @RequestParam Optional<Boolean> hasPickUpService,
                                                         Pageable pageable){
        Slice<PlaceCard> placeCards = restaurantRepository.findRestaurantCardsByUsageInformations(campus, foodType, hasDeliveryService, hasPickUpService, pageable);
        return new SliceDTO<>(placeCards);
    }

    //usage values 조회
    @GetMapping("/place/detail/{placeID}/restaurant")
    public RestaurantUsageValuesContainer getUsageValues(@PathVariable Long placeID) {
        Restaurant restaurant = restaurantRepository.findById(placeID).orElseThrow(BadPlaceIDException::new);
        return RestaurantUsageValuesContainer.fromEntity(restaurant);
    }

}
