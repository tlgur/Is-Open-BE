package com.tlgur.isOpen.repository.place;

import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.dto.PlaceCard;
import com.tlgur.isOpen.dto.SchoolPlaceUsageInformationRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface SchoolPlaceRepositoryCustom {
    Slice<PlaceCard> findSchoolPlaceCardByUsageInformations(Campus campus, SchoolPlaceUsageInformationRequest schoolPlaceUsageInformationRequest, Pageable pageable);
}
