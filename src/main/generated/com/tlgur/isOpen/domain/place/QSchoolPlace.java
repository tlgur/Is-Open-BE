package com.tlgur.isOpen.domain.place;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchoolPlace is a Querydsl query type for SchoolPlace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchoolPlace extends EntityPathBase<SchoolPlace> {

    private static final long serialVersionUID = -2022505971L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSchoolPlace schoolPlace = new QSchoolPlace("schoolPlace");

    public final com.tlgur.isOpen.domain.QPlace _super;

    // inherited
    public final com.tlgur.isOpen.domain.QAddressInformation addressInformation;

    //inherited
    public final StringPath contact;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt;

    //inherited
    public final StringPath createdBy;

    //inherited
    public final StringPath description;

    public final EnumPath<com.tlgur.isOpen.domain.enums.Eatable> eatable = createEnum("eatable", com.tlgur.isOpen.domain.enums.Eatable.class);

    public final BooleanPath hasConsent = createBoolean("hasConsent");

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final ListPath<com.tlgur.isOpen.domain.Image, com.tlgur.isOpen.domain.QImage> images;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt;

    //inherited
    public final StringPath lastModifiedBy;

    // inherited
    public final com.tlgur.isOpen.domain.QMapInformation locationInformation;

    // inherited
    public final com.tlgur.isOpen.domain.QUser manager;

    //inherited
    public final StringPath name;

    // inherited
    public final com.tlgur.isOpen.domain.QOperatingInformation operatingInformation;

    //inherited
    public final EnumPath<com.tlgur.isOpen.domain.enums.PlaceType> placeType;

    //inherited
    public final ListPath<com.tlgur.isOpen.domain.Review, com.tlgur.isOpen.domain.QReview> reviews;

    public final EnumPath<com.tlgur.isOpen.domain.enums.Talkable> talkable = createEnum("talkable", com.tlgur.isOpen.domain.enums.Talkable.class);

    public final EnumPath<com.tlgur.isOpen.domain.enums.Typeable> typeable = createEnum("typeable", com.tlgur.isOpen.domain.enums.Typeable.class);

    public QSchoolPlace(String variable) {
        this(SchoolPlace.class, forVariable(variable), INITS);
    }

    public QSchoolPlace(Path<? extends SchoolPlace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSchoolPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSchoolPlace(PathMetadata metadata, PathInits inits) {
        this(SchoolPlace.class, metadata, inits);
    }

    public QSchoolPlace(Class<? extends SchoolPlace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.tlgur.isOpen.domain.QPlace(type, metadata, inits);
        this.addressInformation = _super.addressInformation;
        this.contact = _super.contact;
        this.createdAt = _super.createdAt;
        this.createdBy = _super.createdBy;
        this.description = _super.description;
        this.id = _super.id;
        this.images = _super.images;
        this.lastModifiedAt = _super.lastModifiedAt;
        this.lastModifiedBy = _super.lastModifiedBy;
        this.locationInformation = _super.locationInformation;
        this.manager = _super.manager;
        this.name = _super.name;
        this.operatingInformation = _super.operatingInformation;
        this.placeType = _super.placeType;
        this.reviews = _super.reviews;
    }

}

