package com.tlgur.isOpen.repository.place;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tlgur.isOpen.domain.enums.Campus;
import com.tlgur.isOpen.domain.enums.FoodType;
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
import static com.tlgur.isOpen.domain.place.QRestaurant.restaurant;
import static com.tlgur.isOpen.domain.place.QSchoolPlace.schoolPlace;

@Slf4j
@RequiredArgsConstructor
public class RestaurantRepositoryCustomImpl implements RestaurantRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<PlaceCard> findRestaurantCardsByUsageInformations(Campus campus, Optional<FoodType> foodType, Optional<Boolean> hasDeliveryService, Optional<Boolean> hasPickUpService, Pageable pageable) {
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
                ).from(restaurant)
                .leftJoin(restaurant.operatingInformation, operatingInformation)
                .leftJoin(restaurant.addressInformation, addressInformation)
                .leftJoin(restaurant.images, image).on(restaurant.id.eq(image.place.id))
                .where(addressInformation.campus.eq(campus).and(restaurantEq(foodType, hasDeliveryService, hasPickUpService)))
                .groupBy(restaurant.id)
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

    private BooleanBuilder restaurantEq(Optional<FoodType> foodType, Optional<Boolean> hasDeliveryService, Optional<Boolean> hasPickUpService) {
        BooleanBuilder restaurantEq = new BooleanBuilder();
        foodType.ifPresent(
                clue -> restaurantEq.and(restaurant.foodType.eq(clue))
        );
        hasDeliveryService.ifPresent(
                clue -> restaurantEq.and(restaurant.hasDeliveryService.eq(clue))
        );
        hasPickUpService.ifPresent(
                clue -> restaurantEq.and(restaurant.hasPickUpService.eq(clue))
        );
        return restaurantEq;
    }
}
