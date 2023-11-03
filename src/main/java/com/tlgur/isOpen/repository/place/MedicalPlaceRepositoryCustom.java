package com.tlgur.isOpen.repository.place;

import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.FoodType;
import com.tlgur.isOpen.domain.enums.MedicalType;
import com.tlgur.isOpen.domain.place.MedicalPlace;
import com.tlgur.isOpen.dto.PlaceCard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;

public interface MedicalPlaceRepositoryCustom {
    Slice<PlaceCard> findMedicalPlaceCardsByUsageInformations(Campus campus, Optional<MedicalType> medicalType, Pageable pageable);
}
