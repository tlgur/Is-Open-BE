package com.tlgur.isOpen.dto;

import com.tlgur.isOpen.domain.enums.Eatable;
import com.tlgur.isOpen.domain.enums.Talkable;
import com.tlgur.isOpen.domain.enums.Typeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolPlaceUsageInformationRequest {
    private Optional<Talkable> talkable = Optional.empty();
    private Optional<Eatable> eatable = Optional.empty();
    private Optional<Typeable> typeable = Optional.empty();
    private Optional<Boolean> hasConsent = Optional.empty();
}
