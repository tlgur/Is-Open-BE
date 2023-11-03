package com.tlgur.isOpen.dto;

import com.tlgur.isOpen.domain.enums.FoodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantUsageInformationRequest {
    private Optional<FoodType> foodType;
    private Optional<Boolean> hasDeliveryService;
    private Optional<Boolean> hasPickUpService;
}
