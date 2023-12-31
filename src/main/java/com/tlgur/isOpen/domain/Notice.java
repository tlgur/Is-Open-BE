package com.tlgur.isOpen.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED)
public class Notice extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(Notice noticeUpdateInfo) {
        this.title = noticeUpdateInfo.getTitle();
        this.content = noticeUpdateInfo.getContent();
    }
}
