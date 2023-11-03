package com.tlgur.isOpen.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class MedicalUsageInformationsContainer {
    private Integer count;
    private List<String> medicalType;

    public MedicalUsageInformationsContainer() {
        this.count = 1;
        this.medicalType = Arrays.asList("약국", "정형외과", "한의원", "안과", "내과", "피부과", "이비인후과");
    }
}
