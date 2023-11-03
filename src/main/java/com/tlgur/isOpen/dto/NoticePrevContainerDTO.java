package com.tlgur.isOpen.dto;

import com.tlgur.isOpen.dto.NoticePrev;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticePrevContainerDTO {
    private Integer noticePrevsCount;
    @Builder.Default
    private List<NoticePrev> noticePrevs = new ArrayList<>();

    public NoticePrevContainerDTO(List<NoticePrev> recentNoticePrevs) {
        noticePrevsCount = recentNoticePrevs.size();
        noticePrevs = recentNoticePrevs;
    }
}
