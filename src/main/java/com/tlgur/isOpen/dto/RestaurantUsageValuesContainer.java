package com.tlgur.isOpen.dto;

import com.tlgur.isOpen.domain.enums.Eatable;
import com.tlgur.isOpen.domain.enums.FoodType;
import com.tlgur.isOpen.domain.enums.Talkable;
import com.tlgur.isOpen.domain.enums.Typeable;
import com.tlgur.isOpen.domain.place.Restaurant;
import com.tlgur.isOpen.domain.place.SchoolPlace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantUsageValuesContainer {
    private FoodType foodType;
    private Boolean hasDeliveryService;
    private Boolean hasPickUpService;

    public static RestaurantUsageValuesContainer fromEntity(Restaurant restaurant) {
        return RestaurantUsageValuesContainer.builder()
                .foodType(restaurant.getFoodType())
                .hasDeliveryService(restaurant.getHasDeliveryService())
                .hasPickUpService(restaurant.getHasPickUpService())
                .build();
    }
}
