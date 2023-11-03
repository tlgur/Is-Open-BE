package com.tlgur.isOpen.dto;

import com.tlgur.isOpen.domain.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
    private String title;
    private String content;

    public static NoticeDTO fromEntity(Notice notice) {
        return NoticeDTO.builder()
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }
}