package com.tlgur.isOpen.repository.place;

import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.dto.PlaceCard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;

public interface PcCafeRepositoryCustom {
    Slice<PlaceCard> findPcCafeCardsByCampus(@Param("campus") Campus campus, Pageable pageable);
}
