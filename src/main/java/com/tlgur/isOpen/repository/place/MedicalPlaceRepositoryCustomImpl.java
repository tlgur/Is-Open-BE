package com.tlgur.isOpen.repository.place;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.MedicalType;
import com.tlgur.isOpen.domain.place.QMedicalPlace;
import com.tlgur.isOpen.dto.PlaceCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.Optional;

import static com.tlgur.isOpen.domain.QAddressInformation.addressInformation;
import static com.tlgur.isOpen.domain.QImage.image;
import static com.tlgur.isOpen.domain.QOperatingInformation.operatingInformation;
import static com.tlgur.isOpen.domain.QPlace.place;
import static com.tlgur.isOpen.domain.place.QMedicalPlace.medicalPlace;
import static com.tlgur.isOpen.domain.place.QSchoolPlace.schoolPlace;

@Slf4j
@RequiredArgsConstructor
public class MedicalPlaceRepositoryCustomImpl implements MedicalPlaceRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PlaceCard> findMedicalPlaceCardsByUsageInformations(Campus campus, Optional<MedicalType> medicalType, Pageable pageable) {
        List<PlaceCard> result = queryFactory
                .select(Projections.bean(
                                PlaceCard.class,
                                place.id.as("placeID"),
                                place.name,
                                operatingInformation.openAt,
                                operatingInformation.closeAt,
                                addressInformation.loadNameAddress,
                                image.originalName.as("imgOriginalName"),
                                image.savedName.as("imgSavedName")
                        )
                ).from(medicalPlace)
                .leftJoin(medicalPlace.operatingInformation, operatingInformation)
                .leftJoin(medicalPlace.addressInformation, addressInformation)
                .leftJoin(medicalPlace.images, image).on(medicalPlace.id.eq(image.place.id))
                .where(addressInformation.campus.eq(campus).and(medicalPlaceEq(medicalType)))
                .groupBy(medicalPlace.id)
                .limit(pageable.getPageSize() + 1)
                .offset(pageable.getOffset())
                .fetch();

        boolean hasNext = false;
        if (result.size() > pageable.getPageSize()) {
            result.remove(result.size() - 1);
            hasNext = true;
        }
        return new SliceImpl<>(result, pageable, hasNext);
    }

    private BooleanBuilder medicalPlaceEq(Optional<MedicalType> medicalType) {
        BooleanBuilder medicalPlaceEq = new BooleanBuilder();
        medicalType.ifPresent(
                clue -> medicalPlaceEq.and(medicalPlace.medicalType.eq(clue))
        );
        return medicalPlaceEq;
    }
}
