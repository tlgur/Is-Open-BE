package com.tlgur.isOpen.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Review extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String title;
    private Integer score;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User writer;
    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;
}
