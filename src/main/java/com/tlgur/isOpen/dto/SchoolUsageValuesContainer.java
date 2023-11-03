package com.tlgur.isOpen.dto;

import com.tlgur.isOpen.domain.enums.Eatable;
import com.tlgur.isOpen.domain.enums.Talkable;
import com.tlgur.isOpen.domain.enums.Typeable;
import com.tlgur.isOpen.domain.place.SchoolPlace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolUsageValuesContainer {
    private Talkable talkable;
    private Eatable eatable;
    private Typeable typeable;
    private Boolean hasConsent;

    public static SchoolUsageValuesContainer fromEntity(SchoolPlace schoolPlace) {
        return SchoolUsageValuesContainer.builder()
                .talkable(schoolPlace.getTalkable())
                .eatable(schoolPlace.getEatable())
                .typeable(schoolPlace.getTypeable())
                .hasConsent(schoolPlace.getHasConsent())
                .build();
    }
}
