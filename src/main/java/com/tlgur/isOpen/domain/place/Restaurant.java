package com.tlgur.isOpen.domain.place;

import com.tlgur.isOpen.domain.*;
import com.tlgur.isOpen.domain.enums.FoodType;
import com.tlgur.isOpen.domain.enums.PlaceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Restaurant extends Place {
    @Enumerated(EnumType.STRING)
    private FoodType foodType;
    private Boolean hasDeliveryService;
    private Boolean hasPickUpService;

    public Restaurant(String name, String contact, String description, User manager, MapInformation locationInformation, AddressInformation addressInformation, OperatingInformation operatingInformation, FoodType foodType, Boolean hasDeliveryService, Boolean hasPickUpService) {
        super(name, contact, description, manager, PlaceType.RESTAURANT, locationInformation, addressInformation, operatingInformation);
        this.foodType = foodType;
        this.hasDeliveryService = hasDeliveryService;
        this.hasPickUpService = hasPickUpService;
    }
}