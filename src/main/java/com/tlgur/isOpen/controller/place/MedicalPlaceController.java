package com.tlgur.isOpen.controller.place;

import com.tlgur.isOpen.domain.enums.MedicalType;
import com.tlgur.isOpen.dto.MedicalUsageInformationsContainer;
import com.tlgur.isOpen.dto.MedicalUsageValuesContainer;
import com.tlgur.isOpen.dto.SliceDTO;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.PlaceType;
import com.tlgur.isOpen.domain.place.MedicalPlace;
import com.tlgur.isOpen.dto.PlaceCard;
import com.tlgur.isOpen.error.exceptions.BadPlaceIDException;
import com.tlgur.isOpen.repository.place.MedicalPlaceRepository;
import com.tlgur.isOpen.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MedicalPlaceController {

    private final PlaceService placeService;
    private final MedicalPlaceRepository medicalPlaceRepository;
    private static final PlaceType placeType = PlaceType.MEDICAL_PLACE;

    //usage list 조회
    @GetMapping("/place/usages/medical")
    public MedicalUsageInformationsContainer getUsageInformation() {
        return new MedicalUsageInformationsContainer();
    }

    //Place Type : MedicalPlace
    //All Place Type Card List 조회 By & Campus & 세부 카테고리
    @GetMapping("/place/cards/medical")
    public SliceDTO<PlaceCard> getPlaceCardsOfMedicalPlace(@RequestParam Campus campus, @RequestParam Optional<MedicalType> medicalType, Pageable pageable){
        Slice<PlaceCard> placeCards = medicalPlaceRepository.findMedicalPlaceCardsByUsageInformations(campus, medicalType, pageable);
        return new SliceDTO<>(placeCards);
    }

    //usage values 조회
    @GetMapping("/place/detail/{placeID}/medical")
    public MedicalUsageValuesContainer getUsageValues(@PathVariable Long placeID) {
        MedicalPlace medicalPlace = medicalPlaceRepository.findById(placeID).orElseThrow(BadPlaceIDException::new);
        return MedicalUsageValuesContainer.fromEntity(medicalPlace);
    }
}
