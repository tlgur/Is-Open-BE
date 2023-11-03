package com.tlgur.isOpen.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class RestaurantUsageInformationsContainer {
    private Integer count;
    private List<String> foodType;
    private List<String> hasDeliveryService;
    private List<String> hasPickUpService;

    public RestaurantUsageInformationsContainer() {
        this.count = 3;
        this.foodType = Arrays.asList("한식","중식","일식","양식","기타간편식");
        this.hasDeliveryService = Arrays.asList("TRUE", "FALSE");
        this.hasPickUpService = Arrays.asList("TRUE", "FALSE");
    }
}
