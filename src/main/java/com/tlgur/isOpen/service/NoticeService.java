package com.tlgur.isOpen.service;

import com.tlgur.isOpen.domain.Notice;
import com.tlgur.isOpen.dto.NoticePrev;
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
}
