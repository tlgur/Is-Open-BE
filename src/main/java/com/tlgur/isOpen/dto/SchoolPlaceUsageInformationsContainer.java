package com.tlgur.isOpen.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class SchoolPlaceUsageInformationsContainer {
    private Integer count;
    private List<String> talkable;
    private List<String> eatable;
    private List<String> typeable;
    private List<String> hasConsent;

    public SchoolPlaceUsageInformationsContainer() {
        count = 4;
        this.talkable = Arrays.asList("ANY", "SMALL_TALK", "DISCUSSION", "NO");
        this.eatable = Arrays.asList("ANY", "WATER_ONLY", "BEVERAGE", "SNACK", "NO");
        this.typeable = Arrays.asList("ANY", "NO", "SILENT_IO");
        this.hasConsent = Arrays.asList("TRUE", "FALSE");
    }
}
