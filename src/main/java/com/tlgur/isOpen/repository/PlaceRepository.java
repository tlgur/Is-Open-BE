package com.tlgur.isOpen.repository;

import com.tlgur.isOpen.domain.Place;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.dto.PlaceCard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query("select new com.tlgur.isOpen.dto.PlaceCard(" +
            "p.id, p.name, o.openAt, o.closeAt, a.loadNameAddress, i.savedName, i.originalName" +
            ") from Place p inner join p.addressInformation a " +
            "inner join p.operatingInformation o " +
            "left join p.images i on p.id = i.place.id " +
            "where a.campus = :campus " +
            "group by p.id")
    Slice<PlaceCard> findPlaceCardsByCampus(@Param("campus") Campus campus, Pageable pageable);

    @Query("select new com.tlgur.isOpen.dto.PlaceCard(" +
            "p.id, p.name, o.openAt, o.closeAt, a.loadNameAddress, i.savedName, i.originalName" +
            ") from Place p inner join p.addressInformation a " +
            "inner join p.operatingInformation o " +
            "left join p.images i on p = i.place " +
            "where a.campus = :campus and p.name like %:keyword% " +
            "group by p.id")
    Slice<PlaceCard> findPlaceCardsByCampusAndKeyword(@Param("campus") Campus campus, @Param("keyword") String keyword, Pageable pageable);

    @EntityGraph(attributePaths = {
                            "operatingInformation", "locationInformation","addressInformation","operatingInformation","images"
                    })
    Optional<Place> findPlaceDetailById(Long id);

}
