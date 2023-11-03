package com.tlgur.isOpen.repository.place;

import com.tlgur.isOpen.domain.place.SchoolPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolPlaceRepository extends JpaRepository<SchoolPlace, Long>, SchoolPlaceRepositoryCustom {
}
