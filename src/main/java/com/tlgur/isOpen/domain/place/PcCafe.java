package com.tlgur.isOpen.domain.place;

import com.tlgur.isOpen.domain.*;
import com.tlgur.isOpen.domain.enums.PlaceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PcCafe extends Place {
    public PcCafe(String name, String contact, String description, User manager, MapInformation locationInformation, AddressInformation addressInformation, OperatingInformation operatingInformation) {
        super(name, contact, description, manager, PlaceType.PC_CAFE, locationInformation, addressInformation, operatingInformation);
    }
}
