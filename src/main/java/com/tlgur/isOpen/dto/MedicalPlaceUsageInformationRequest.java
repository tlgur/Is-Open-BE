package com.tlgur.isOpen.dto;

import com.tlgur.isOpen.domain.enums.MedicalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalPlaceUsageInformationRequest {
    private Optional<MedicalType> medicalType;
}
