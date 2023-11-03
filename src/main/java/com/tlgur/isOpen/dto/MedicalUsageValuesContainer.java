package com.tlgur.isOpen.dto;

import com.tlgur.isOpen.domain.enums.MedicalType;
import com.tlgur.isOpen.domain.place.MedicalPlace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalUsageValuesContainer {
    private MedicalType medicalType;

    public static MedicalUsageValuesContainer fromEntity(MedicalPlace medicalPlace) {
        return MedicalUsageValuesContainer.builder()
                .medicalType(medicalPlace.getMedicalType())
                .build();
    }
}
