package com.tlgur.isOpen.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOperatingInformation is a Querydsl query type for OperatingInformation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOperatingInformation extends EntityPathBase<OperatingInformation> {

    private static final long serialVersionUID = -1835777270L;

    public static final QOperatingInformation operatingInformation = new QOperatingInformation("operatingInformation");

    public final StringPath breakDay = createString("breakDay");

    public final TimePath<java.time.LocalTime> closeAt = createTime("closeAt", java.time.LocalTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath note = createString("note");

    public final TimePath<java.time.LocalTime> openAt = createTime("openAt", java.time.LocalTime.class);

    public QOperatingInformation(String variable) {
        super(OperatingInformation.class, forVariable(variable));
    }

    public QOperatingInformation(Path<? extends OperatingInformation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOperatingInformation(PathMetadata metadata) {
        super(OperatingInformation.class, metadata);
    }

}

