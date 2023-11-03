package com.tlgur.isOpen.controller.place;

import com.tlgur.isOpen.dto.MartUsageInformationsContainer;
import com.tlgur.isOpen.dto.SliceDTO;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.PlaceType;
import com.tlgur.isOpen.dto.PlaceCard;
import com.tlgur.isOpen.repository.place.MartRepository;
import com.tlgur.isOpen.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:63342")
public class MartController {

    private final PlaceService placeService;
    private final MartRepository martRepository;
    private static final PlaceType placeType = PlaceType.MART;

    //usage list 조회
    @GetMapping("/place/usages/mart")
    public MartUsageInformationsContainer getUsageInformation() {
        return new MartUsageInformationsContainer();
    }

    //Place Type : Mart
    //Card List 조회 By & Campus & 세부 카테고리
    @GetMapping("/place/cards/mart")
    public SliceDTO<PlaceCard> getPlaceCardsOfCafe(@RequestParam Campus campus, Pageable pageable){
        Slice<PlaceCard> placeCards = martRepository.findMartCardsByCampus(campus, pageable);
        return new SliceDTO<>(placeCards);
    }
}
