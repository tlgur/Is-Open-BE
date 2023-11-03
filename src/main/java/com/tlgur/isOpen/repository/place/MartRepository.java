package com.tlgur.isOpen.repository.place;

import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.place.Mart;
import com.tlgur.isOpen.dto.PlaceCard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MartRepository extends JpaRepository<Mart, Long>, MartRepositoryCustom {
}
