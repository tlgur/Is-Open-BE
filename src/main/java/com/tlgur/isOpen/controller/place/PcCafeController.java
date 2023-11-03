package com.tlgur.isOpen.controller.place;

import com.tlgur.isOpen.dto.PcCafeUsageInformationsContainer;
import com.tlgur.isOpen.dto.SliceDTO;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.PlaceType;
import com.tlgur.isOpen.dto.PlaceCard;
import com.tlgur.isOpen.repository.place.PcCafeRepository;
import com.tlgur.isOpen.service.PlaceService;
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
public class PcCafeController {
    private final PlaceService placeService;
    private final PcCafeRepository pcCafeRepository;
    private static final PlaceType placeType = PlaceType.PC_CAFE;

    //usage list 조회
    @GetMapping("/place/usages/pc")
    public PcCafeUsageInformationsContainer getUsageInformation() throws ClassNotFoundException {
        return new PcCafeUsageInformationsContainer();
    }

    //Place Type : PcCafe
    //All Place Type Card List 조회 By & Campus & 세부 카테고리
    @GetMapping("/place/cards/pc")
    public SliceDTO<PlaceCard> getPlaceCardsOfPcCafe(@RequestParam Campus campus, Pageable pageable){
        Slice<PlaceCard> placeCards = pcCafeRepository.findPcCafeCardsByCampus(campus, pageable);
        return new SliceDTO<>(placeCards);
    }
}
