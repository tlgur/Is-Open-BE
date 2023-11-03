package com.tlgur.isOpen.domain.place;

import com.tlgur.isOpen.domain.*;
import com.tlgur.isOpen.domain.enums.Eatable;
import com.tlgur.isOpen.domain.enums.PlaceType;
import com.tlgur.isOpen.domain.enums.Talkable;
import com.tlgur.isOpen.domain.enums.Typeable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SchoolPlace extends Place {
    @Enumerated(EnumType.STRING)
    private Talkable talkable;
    @Enumerated(EnumType.STRING)
    private Eatable eatable;
    @Enumerated(EnumType.STRING)
    private Typeable typeable;
    private Boolean hasConsent;

    public SchoolPlace(String name, String contact, String description, User manager, MapInformation locationInformation, AddressInformation addressInformation, OperatingInformation operatingInformation, Talkable talkable, Eatable eatable, Typeable typeable, Boolean hasConsent) {
        super(name, contact, description, manager, PlaceType.SCHOOL_PLACE, locationInformation, addressInformation, operatingInformation);
        this.talkable = talkable;
        this.eatable = eatable;
        this.typeable = typeable;
        this.hasConsent = hasConsent;
    }
}
