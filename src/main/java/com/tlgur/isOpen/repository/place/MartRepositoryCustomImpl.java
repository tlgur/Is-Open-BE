package com.tlgur.isOpen.repository.place;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.place.QMart;
import com.tlgur.isOpen.dto.PlaceCard;
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
import static com.tlgur.isOpen.domain.place.QMart.mart;
import static com.tlgur.isOpen.domain.place.QPcCafe.pcCafe;

@Slf4j
@RequiredArgsConstructor
public class MartRepositoryCustomImpl implements MartRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PlaceCard> findMartCardsByCampus(Campus campus, Pageable pageable) {
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
                ).from(mart)
                .leftJoin(mart.operatingInformation, operatingInformation)
                .leftJoin(mart.addressInformation, addressInformation)
                .leftJoin(mart.images, image).on(mart.id.eq(image.place.id))
                .where(addressInformation.campus.eq(campus))
                .groupBy(mart.id)
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
}
