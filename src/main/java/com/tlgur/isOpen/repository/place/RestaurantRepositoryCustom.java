package com.tlgur.isOpen.repository.place;

import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.FoodType;
import com.tlgur.isOpen.dto.PlaceCard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface RestaurantRepositoryCustom {
    Slice<PlaceCard> findRestaurantCardsByUsageInformations(Campus campus, Optional<FoodType> foodType, Optional<Boolean> hasDeliveryService, Optional<Boolean> hasPickUpService, Pageable pageable);
}
