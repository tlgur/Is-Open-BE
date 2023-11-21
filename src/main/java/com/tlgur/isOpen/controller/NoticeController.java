package com.tlgur.isOpen.controller;

import com.tlgur.isOpen.dto.NoticePrevContainerDTO;
import com.tlgur.isOpen.domain.Notice;
import com.tlgur.isOpen.error.exceptions.NoMatchNoticeIDException;
import com.tlgur.isOpen.repository.NoticeRepository;
import com.tlgur.isOpen.dto.NoticeDTO;
import com.tlgur.isOpen.dto.NoticePrev;
import com.tlgur.isOpen.service.NoticeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeRepository noticeRepository;

    //공지 작성
    @PostMapping("/notice")
    public NoticePrev createNotice(@RequestParam @NotBlank String title, @RequestParam @NotBlank String content) {
        Notice notice = new Notice(title, content);
        NoticePrev noticePrev = noticeService.saveNotice(notice);
        return noticePrev;
    }

    @PatchMapping("/notice/{noticeId}")
    public NoticePrev updateNotice(@PathVariable Long noticeId, @RequestParam @NotBlank String title, @RequestParam @NotBlank String content) {

    }

    //최근 공지 조회(5개)
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
