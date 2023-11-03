package com.tlgur.isOpen.repository.place;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.dto.PlaceCard;
import com.tlgur.isOpen.dto.SchoolPlaceUsageInformationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.tlgur.isOpen.domain.QAddressInformation.addressInformation;
import static com.tlgur.isOpen.domain.QImage.image;
import static com.tlgur.isOpen.domain.QOperatingInformation.operatingInformation;
import static com.tlgur.isOpen.domain.QPlace.place;
import static com.tlgur.isOpen.domain.place.QSchoolPlace.schoolPlace;

@Slf4j
@RequiredArgsConstructor
public class SchoolPlaceRepositoryCustomImpl implements SchoolPlaceRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PlaceCard> findSchoolPlaceCardByUsageInformations(Campus campus, SchoolPlaceUsageInformationRequest schoolPlaceUsageInformationRequest, Pageable pageable) {
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
                ).from(schoolPlace)
                .leftJoin(schoolPlace.operatingInformation, operatingInformation)
                .leftJoin(schoolPlace.addressInformation, addressInformation)
                .leftJoin(schoolPlace.images, image).on(schoolPlace.id.eq(image.place.id))
                .where(addressInformation.campus.eq(campus).and(schoolPlaceEq(schoolPlaceUsageInformationRequest)))
                .groupBy(schoolPlace.id)
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

    private BooleanBuilder schoolPlaceEq(SchoolPlaceUsageInformationRequest req) {
        BooleanBuilder schoolPlaceEq = new BooleanBuilder();
        req.getEatable().ifPresent(
                clue -> schoolPlaceEq.and(schoolPlace.eatable.eq(clue))
        );
        req.getTalkable().ifPresent(
                clue -> schoolPlaceEq.and(schoolPlace.talkable.eq(clue))
        );
        req.getTypeable().ifPresent(
                clue -> schoolPlaceEq.and(schoolPlace.typeable.eq(clue))
        );
        req.getHasConsent().ifPresent(
                clue -> schoolPlaceEq.and(schoolPlace.hasConsent.eq(clue))
        );
        return schoolPlaceEq;
    }
}
