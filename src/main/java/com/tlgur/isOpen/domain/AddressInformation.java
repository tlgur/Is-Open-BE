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
public class AddressInformation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "address_information_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Campus campus;

    private String loadNameAddress;
    private String buildingNameAddress;
}
