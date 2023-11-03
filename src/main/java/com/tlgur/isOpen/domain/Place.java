package com.tlgur.isOpen.domain;

import com.tlgur.isOpen.domain.enums.PlaceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
public class Place extends BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "place_id")
    private Long id;

    private String name;
    private String contact;
    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User manager;

    @Enumerated(EnumType.STRING)
    @Column(name = "place_type", updatable = false)
    private PlaceType placeType;

    @OneToOne
    @JoinColumn(name = "map_information_id")
    private MapInformation locationInformation;
    @OneToOne
    @JoinColumn(name = "address_information_id")
    private AddressInformation addressInformation;
    @OneToOne
    @JoinColumn(name = "operating_information_id")
    private OperatingInformation operatingInformation;

    @OneToMany(mappedBy = "place")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "place")
    private List<Review> reviews = new ArrayList<>();


    protected Place(String name, String contact, String description, User manager, PlaceType placeType, MapInformation locationInformation, AddressInformation addressInformation, OperatingInformation operatingInformation) {
        this.name = name;
        this.contact = contact;
        this.description = description;
        this.manager = manager;
        this.placeType = placeType;
        this.locationInformation = locationInformation;
        this.addressInformation = addressInformation;
        this.operatingInformation = operatingInformation;
    }

    public OptionalDouble calculateScore() {
        if(reviews.isEmpty()) return OptionalDouble.empty();
        return reviews.stream()
                .mapToInt(Review::getScore)
                .average();
    }
}
