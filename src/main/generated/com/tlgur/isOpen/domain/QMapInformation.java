package com.tlgur.isOpen.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMapInformation is a Querydsl query type for MapInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMapInformation extends EntityPathBase<MapInformation> {

    private static final long serialVersionUID = 2058319695L;

    public static final QMapInformation mapInformation = new QMapInformation("mapInformation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public QMapInformation(String variable) {
        super(MapInformation.class, forVariable(variable));
    }

    public QMapInformation(Path<? extends MapInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMapInformation(PathMetadata metadata) {
        super(MapInformation.class, metadata);
    }

}

