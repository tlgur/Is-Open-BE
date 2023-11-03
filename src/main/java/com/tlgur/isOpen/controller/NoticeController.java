package com.tlgur.isOpen.controller;

import com.tlgur.isOpen.dto.NoticePrevContainerDTO;
import com.tlgur.isOpen.domain.Notice;
import com.tlgur.isOpen.error.exceptions.NoMatchNoticeIDException;
import com.tlgur.isOpen.repository.NoticeRepository;
import com.tlgur.isOpen.dto.NoticeDTO;
import com.tlgur.isOpen.dto.NoticePrev;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/notice/recent")
    public NoticePrevContainerDTO getRecentNotices() {
        List<NoticePrev> recentNoticePrevs = noticeRepository.getNoticePrevsRecent();
        return new NoticePrevContainerDTO(recentNoticePrevs);
    }

    //공지 상세 조회
    @GetMapping("/notice/{noticeID}")
    public NoticeDTO getNotice(@PathVariable Long noticeID) {
        Notice notice = noticeRepository.findById(noticeID).orElseThrow(NoMatchNoticeIDException::new);
        NoticeDTO noticeDTO = NoticeDTO.fromEntity(notice);
        return noticeDTO;
    }
}
