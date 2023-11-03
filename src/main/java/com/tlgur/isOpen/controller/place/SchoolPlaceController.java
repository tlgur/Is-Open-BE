package com.tlgur.isOpen.controller.place;

import com.tlgur.isOpen.domain.place.MedicalPlace;
import com.tlgur.isOpen.domain.place.SchoolPlace;
import com.tlgur.isOpen.dto.*;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.PlaceType;
import com.tlgur.isOpen.error.exceptions.BadPlaceIDException;
import com.tlgur.isOpen.repository.place.SchoolPlaceRepository;
import com.tlgur.isOpen.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SchoolPlaceController {

    private final PlaceService placeService;
    private final SchoolPlaceRepository schoolPlaceRepository;
    private static final PlaceType placeType = PlaceType.SCHOOL_PLACE;

    //usage list 조회
    @GetMapping("/place/usages/school")
    public SchoolPlaceUsageInformationsContainer getUsageInformation() {
        return new SchoolPlaceUsageInformationsContainer();
    }

    //Place Type : SchoolPlace
    //All Place Type Card List 조회 By & Campus & 세부 카테고리
    @GetMapping("/place/cards/school")
    public SliceDTO<PlaceCard> getPlaceCardsOfSchoolPlace(@RequestParam Campus campus, @ModelAttribute SchoolPlaceUsageInformationRequest schoolPlaceUsageInformationRequest, Pageable pageable) {
        log.info("schoolPlaceUsageInformationRequest : {}", schoolPlaceUsageInformationRequest);
        Slice<PlaceCard> placeCards = schoolPlaceRepository.findSchoolPlaceCardByUsageInformations(campus, schoolPlaceUsageInformationRequest, pageable);
        return new SliceDTO<>(placeCards);
    }

    //usage values 조회
    @GetMapping("/place/detail/{placeID}/school")
    public SchoolUsageValuesContainer getUsageValues(@PathVariable Long placeID) {
        SchoolPlace schoolPlace = schoolPlaceRepository.findById(placeID).orElseThrow(BadPlaceIDException::new);
        return SchoolUsageValuesContainer.fromEntity(schoolPlace);
    }
}
