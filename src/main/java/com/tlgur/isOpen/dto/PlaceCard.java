package com.tlgur.isOpen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
public class PlaceCard {
    private Long placeID;
    private String name;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openAt;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeAt;
    private String loadNameAddress;
    private String imgSavedName;
    private String imgOriginalName;

    public PlaceCard(Long placeID, String name, LocalTime openAt, LocalTime closeAt, String loadNameAddress, String imgSavedName, String imgOriginalName) {
        this.placeID = placeID;
        this.name = name;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.loadNameAddress = loadNameAddress;
        this.imgSavedName = imgSavedName;
        this.imgOriginalName = imgOriginalName;
    }
}
