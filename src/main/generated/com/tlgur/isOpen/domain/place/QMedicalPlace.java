package com.tlgur.isOpen.domain.place;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMedicalPlace is a Querydsl query type for MedicalPlace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMedicalPlace extends EntityPathBase<MedicalPlace> {

    private static final long serialVersionUID = 563861532L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMedicalPlace medicalPlace = new QMedicalPlace("medicalPlace");

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

    public final EnumPath<com.tlgur.isOpen.domain.enums.MedicalType> medicalType = createEnum("medicalType", com.tlgur.isOpen.domain.enums.MedicalType.class);

    //inherited
    public final StringPath name;

    // inherited
    public final com.tlgur.isOpen.domain.QOperatingInformation operatingInformation;

    //inherited
    public final EnumPath<com.tlgur.isOpen.domain.enums.PlaceType> placeType;

    //inherited
    public final ListPath<com.tlgur.isOpen.domain.Review, com.tlgur.isOpen.domain.QReview> reviews;

    public QMedicalPlace(String variable) {
        this(MedicalPlace.class, forVariable(variable), INITS);
    }

    public QMedicalPlace(Path<? extends MedicalPlace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMedicalPlace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMedicalPlace(PathMetadata metadata, PathInits inits) {
        this(MedicalPlace.class, metadata, inits);
    }

    public QMedicalPlace(Class<? extends MedicalPlace> type, PathMetadata metadata, PathInits inits) {
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

