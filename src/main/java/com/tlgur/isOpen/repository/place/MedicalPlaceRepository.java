package com.tlgur.isOpen.repository.place;

import com.tlgur.isOpen.domain.place.MedicalPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalPlaceRepository extends JpaRepository<MedicalPlace, Long>, MedicalPlaceRepositoryCustom {
}
