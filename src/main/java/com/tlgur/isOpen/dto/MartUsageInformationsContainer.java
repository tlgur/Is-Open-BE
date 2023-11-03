package com.tlgur.isOpen.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MartUsageInformationsContainer {
    private Integer count;

    public MartUsageInformationsContainer() {
        this.count = 0;
    }
}
