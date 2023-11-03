package com.tlgur.isOpen.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class PcCafeUsageInformationsContainer {
    private Integer count;

    public PcCafeUsageInformationsContainer() {
        this.count = 0;
    }
}
