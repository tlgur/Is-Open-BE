package com.tlgur.isOpen.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.querydsl.codegen.utils.model.ClassType;
import com.tlgur.isOpen.domain.Place;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.PlaceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDetail {
    private Long placeID;
    private String name;
    private String description;
    private String contact;
    private PlaceType placeType;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime openAt;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeAt;
    private String breakDay;
    private String operatingNote;

    private Campus campus;
    private String loadAddress;
    private String buildingAddress;

    private List<ImageDTO> images;

    public static PlaceDetail fromEntity(Place place) {
        return PlaceDetail.builder()
                .placeID(place.getId())
                .name(place.getName())
                .description(place.getDescription())
                .contact(place.getContact())
                .placeType(place.getPlaceType())
                .openAt(place.getOperatingInformation().getOpenAt())
                .closeAt(place.getOperatingInformation().getCloseAt())
                .breakDay(place.getOperatingInformation().getBreakDay())
                .operatingNote(place.getOperatingInformation().getNote())
                .campus(place.getAddressInformation().getCampus())
                .loadAddress(place.getAddressInformation().getLoadNameAddress())
                .buildingAddress(place.getAddressInformation().getBuildingNameAddress())
                .images(
                        place.getImages().stream()
                                .map(ImageDTO::fromEntity)
                                .collect(Collectors.toList())
                ).build();
    }
}
