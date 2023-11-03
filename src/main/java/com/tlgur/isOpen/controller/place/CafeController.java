package com.tlgur.isOpen.controller.place;

import com.tlgur.isOpen.dto.CafeUsageInformationsContainer;
import com.tlgur.isOpen.dto.SliceDTO;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.PlaceType;
import com.tlgur.isOpen.dto.PlaceCard;
import com.tlgur.isOpen.repository.place.CafeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CafeController {

    private static final PlaceType placeType = PlaceType.CAFE;
    private final CafeRepository cafeRepository;

    //usage list 조회
    @GetMapping("/place/usages/cafe")
    public CafeUsageInformationsContainer getUsageInformation() throws ClassNotFoundException {
        return new CafeUsageInformationsContainer();
    }

    //Place Type : Cafe
    //Card List 조회 By & Campus & 세부 카테고리
    @GetMapping("/place/cards/cafe")
    public SliceDTO<PlaceCard> getPlaceCardsOfMart(@RequestParam Campus campus, Pageable pageable) {
        Slice<PlaceCard> placeCards = cafeRepository.findCafeCardsByCampus(campus, pageable);
        return new SliceDTO<>(placeCards);
    }

}
