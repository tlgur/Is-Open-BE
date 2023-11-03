package com.tlgur.isOpen.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddressInformation is a Querydsl query type for AddressInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAddressInformation extends EntityPathBase<AddressInformation> {

    private static final long serialVersionUID = -984462793L;

    public static final QAddressInformation addressInformation = new QAddressInformation("addressInformation");

    public final StringPath buildingNameAddress = createString("buildingNameAddress");

    public final EnumPath<com.tlgur.isOpen.domain.enums.Campus> campus = createEnum("campus", com.tlgur.isOpen.domain.enums.Campus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath loadNameAddress = createString("loadNameAddress");

    public QAddressInformation(String variable) {
        super(AddressInformation.class, forVariable(variable));
    }

    public QAddressInformation(Path<? extends AddressInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddressInformation(PathMetadata metadata) {
        super(AddressInformation.class, metadata);
    }

}

