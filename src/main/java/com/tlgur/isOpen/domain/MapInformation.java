package com.tlgur.isOpen.domain;

import com.tlgur.isOpen.domain.enums.Campus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MapInformation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "map_information_id")
    private Long id;

    private Double latitude;
    private Double longitude;

    public MapInformation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void changeLocationInformation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
