package com.tlgur.isOpen.repository;

import com.tlgur.isOpen.domain.MapInformation;
import com.tlgur.isOpen.dto.CoordinateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapRepository extends JpaRepository<MapInformation, Long> {
    @Query("select new com.tlgur.isOpen.dto.CoordinateDTO(p.id, p.name, l.latitude, l.longitude)" +
            " from Place p inner join p.locationInformation l where p.id in :placeIDs")
    public List<CoordinateDTO> getCoordinatesByPlaceIds(@Param("placeIDs") List<Long> placeIDs);
}
