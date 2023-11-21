package com.tlgur.isOpen.controller;

import com.tlgur.isOpen.domain.Place;
import com.tlgur.isOpen.dto.PlaceCard;
import com.tlgur.isOpen.dto.PlaceDetail;
import com.tlgur.isOpen.dto.SliceDTO;
import com.tlgur.isOpen.domain.enums.*;
import com.tlgur.isOpen.error.exceptions.BadPlaceIDException;
import com.tlgur.isOpen.repository.PlaceRepository;
import com.tlgur.isOpen.service.PlaceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceRepository placeRepository;

    //Place Card List 조회 By & Campus & 세부 카테고리
    @GetMapping("/place/cards")
    public SliceDTO<PlaceCard> getPlaceCards(@RequestParam Campus campus, Pageable pageable){
        Slice<PlaceCard> placeCards = placeRepository.findPlaceCardsByCampus(campus, pageable);
        return new SliceDTO<>(placeCards);
    }

    //Place Card List 조회 By 검색
    @GetMapping("/place/search/{keyword}")
    public SliceDTO<PlaceCard> searchPlaceCards(@RequestParam Campus campus, @PathVariable String keyword, Pageable pageable) {
        Slice<PlaceCard> placeCards = placeRepository.findPlaceCardsByCampusAndKeyword(campus, keyword, pageable);
        return new SliceDTO<>(placeCards);
    }

    //Place Detail By Place ID
    @GetMapping("/place/detail/{placeID}")
    public PlaceDetail getPlaceDetailInfo(@PathVariable Long placeID) {
        Place place = placeRepository.findPlaceDetailById(placeID)
                .orElseThrow(BadPlaceIDException::new);
        return PlaceDetail.fromEntity(place);
    }
}
