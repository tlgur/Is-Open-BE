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
        return noticeService.saveNotice(notice);
    }

    //공지 수정
    @PatchMapping("/notice/{noticeId}")
    public NoticeDTO updateNotice(@PathVariable Long noticeId, @RequestParam @NotBlank String title, @RequestParam @NotBlank String content) {
        Notice noticeUpdateInfo = new Notice(title, content);
        Notice updatedNotice = noticeService.updateNotice(noticeId, noticeUpdateInfo);
        return NoticeDTO.fromEntity(updatedNotice);
    }

    @DeleteMapping("/notice/{noticeId}")
    public NoticeDTO deleteNotice(@PathVariable Long noticeId) {
        Notice notice = noticeService.removeNotice(noticeId);
        return NoticeDTO.fromEntity(notice);
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
        return NoticeDTO.fromEntity(notice);
    }
}
