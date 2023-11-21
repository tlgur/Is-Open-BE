package com.tlgur.isOpen.dto;

import com.tlgur.isOpen.domain.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class NoticePrev {
    private Long noticeId;
    private String title;

    public NoticePrev(Long noticeId, String title) {
        this.noticeId = noticeId;
        this.title = title;
    }

    public static NoticePrev fromEntity(Notice notice) {
        return NoticePrev.builder()
                .noticeId(notice.getId())
                .title(notice.getTitle())
                .build();
    }
}
