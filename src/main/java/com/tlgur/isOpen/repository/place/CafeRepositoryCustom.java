package com.tlgur.isOpen.repository.place;

import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.dto.PlaceCard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CafeRepositoryCustom {
    Slice<PlaceCard> findCafeCardsByCampus(Campus campus, Pageable pageable);
}
