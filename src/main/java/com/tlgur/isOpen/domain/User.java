package com.tlgur.isOpen.domain;

import com.tlgur.isOpen.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(updatable = false, unique = true)
    private String username;
    private String password;

    private String nickname;
    private String contact;

    @OneToMany(mappedBy = "writer")
    private List<Review> reviews = new ArrayList<>();
}
