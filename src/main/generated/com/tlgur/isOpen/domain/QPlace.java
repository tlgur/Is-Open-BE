package com.tlgur.isOpen.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlace is a Querydsl query type for Place
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPlace extends EntityPathBase<Place> {

    private static final long serialVersionUID = -333735672L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlace place = new QPlace("place");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAddressInformation addressInformation;

    public final StringPath contact = createString("contact");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Image, QImage> images = this.<Image, QImage>createList("images", Image.class, QImage.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final QMapInformation locationInformation;

    public final QUser manager;

    public final StringPath name = createString("name");

    public final QOperatingInformation operatingInformation;

    public final EnumPath<com.tlgur.isOpen.domain.enums.PlaceType> placeType = createEnum("placeType", com.tlgur.isOpen.domain.enums.PlaceType.class);

    public final ListPath<Review, QReview> reviews = this.<Review, QReview>createList("reviews", Review.class, QReview.class, PathInits.DIRECT2);

    public QPlace(String variable) {
        this(Place.class, forVariable(variable), INITS);
    }

    public QPlace(Path<? extends Place> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlace(PathMetadata metadata, PathInits inits) {
        this(Place.class, metadata, inits);
    }

    public QPlace(Class<? extends Place> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.addressInformation = inits.isInitialized("addressInformation") ? new QAddressInformation(forProperty("addressInformation")) : null;
        this.locationInformation = inits.isInitialized("locationInformation") ? new QMapInformation(forProperty("locationInformation")) : null;
        this.manager = inits.isInitialized("manager") ? new QUser(forProperty("manager")) : null;
        this.operatingInformation = inits.isInitialized("operatingInformation") ? new QOperatingInformation(forProperty("operatingInformation")) : null;
    }

}

