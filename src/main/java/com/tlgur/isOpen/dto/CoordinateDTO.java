package com.tlgur.isOpen.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CoordinateDTO {
    private Long placeID;
    private String placeName;
    private Double latitude;
    private Double longitude;

    public CoordinateDTO(Long placeID, String placeName, Double latitude, Double longitude) {
        this.placeID = placeID;
        this.placeName = placeName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
