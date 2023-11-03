package com.tlgur.isOpen.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class OperatingInformation {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "operating_information_id")
    private Long id;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime openAt;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeAt;
    private String breakDay;
    private String note;

    public OperatingInformation(LocalTime openAt, LocalTime closeAt, String breakDay, String note) {
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.breakDay = breakDay;
        this.note = note;
    }
    public void changeOperatingInformation(LocalTime openAt, LocalTime closeAt, String breakDay, String note) {
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.breakDay = breakDay;
        this.note = note;
    }
}
