package com.tlgur.isOpen.service;

import com.tlgur.isOpen.domain.Notice;
import com.tlgur.isOpen.dto.NoticePrev;
import com.tlgur.isOpen.error.exceptions.NoMatchNoticeIDException;
import com.tlgur.isOpen.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticePrev saveNotice(Notice notice) {
        Notice savedNotice = noticeRepository.save(notice);
        return NoticePrev.fromEntity(savedNotice);
    }

    public Notice updateNotice(Long noticeId, Notice noticeUpdateInfo) throws NoMatchNoticeIDException {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(NoMatchNoticeIDException::new);
        notice.update(noticeUpdateInfo);
        return notice;
    }
}
