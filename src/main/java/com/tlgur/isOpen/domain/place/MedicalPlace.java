package com.tlgur.isOpen.domain.place;

import com.tlgur.isOpen.domain.*;
import com.tlgur.isOpen.domain.enums.MedicalType;
import com.tlgur.isOpen.domain.enums.PlaceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MedicalPlace extends Place {
    @Enumerated(EnumType.STRING)
    private MedicalType medicalType;

    public MedicalPlace(String name, String contact, String description, User manager, MapInformation locationInformation, AddressInformation addressInformation, OperatingInformation operatingInformation, MedicalType medicalType) {
        super(name, contact, description, manager, PlaceType.MEDICAL_PLACE, locationInformation, addressInformation, operatingInformation);
        this.medicalType = medicalType;
    }
}