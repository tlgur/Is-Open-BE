package com.tlgur.isOpen.controller;

import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.repository.MapRepository;
import com.tlgur.isOpen.dto.CoordinateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MapController {

    private final MapRepository mapRepository;

    //좌표 조회
    @GetMapping("/map/positions")
    public List<CoordinateDTO> getCoordinated(@RequestParam Campus campus, @RequestParam List<Long> placeIds) {
        mapRepository.getCoordinatesByPlaceIds(placeIds);
        return null;
    }

}
