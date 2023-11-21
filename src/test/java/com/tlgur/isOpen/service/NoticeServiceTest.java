package com.tlgur.isOpen.service;

import com.tlgur.isOpen.TestUtils;
import com.tlgur.isOpen.domain.Notice;
import com.tlgur.isOpen.dto.NoticePrev;
import com.tlgur.isOpen.error.exceptions.NoMatchNoticeIDException;
import com.tlgur.isOpen.repository.NoticeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.tlgur.isOpen.TestUtils.setEntityId;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;

@Slf4j
@Transactional
@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {
    @InjectMocks
    private NoticeService noticeService;
    @Mock
    private NoticeRepository noticeRepository;

    /**
     * input : title & content
     * expect result : Id & title
     */
    @Test
    public void saveNotice_Default_Success() throws Exception {
        //given
        String title = "testNoticeTitle";
        String content = "testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent testNoticeContent ";
        Notice notice = new Notice(title, content);
        Long noticeId = 1234L;

        //mocking
        doAnswer(invocation -> {
            setEntityId(notice, noticeId);
            return notice;
        }).when(noticeRepository).save(notice);

        //when
        NoticePrev noticePrev = noticeService.saveNotice(notice);

        //then
        assertThat(noticePrev.getNoticeId()).isEqualTo(noticeId);
    }

    /**
     * input : title(already Saved) & content
     * expect result : Id & title
     */
    @Test
    public void saveNotice_DuplicatedTitle_Success() throws Exception {
        //given
        String title = "testNoticeTitle";
        String preSavedContent = "testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 testContent1 ";
        String content = "testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 testContent2 ";

        Long preSavedNoticeId = 1234L;
        Long noticeId = 4321L;
        Notice preSavedNotice = new Notice(title, preSavedContent);
        Notice notice = new Notice(title, content);

        //mocking
        doAnswer(invocation -> {
            setEntityId(preSavedNotice, preSavedNoticeId);
            return preSavedNotice;
        }).when(noticeRepository).save(preSavedNotice);
        doAnswer(invocation -> {
            setEntityId(notice, noticeId);
            return notice;
        }).when(noticeRepository).save(notice);

        //when
        noticeService.saveNotice(preSavedNotice);
        NoticePrev noticePrev = noticeService.saveNotice(notice);

        //then
        assertThat(noticePrev.getNoticeId()).isEqualTo(noticeId);
    }

    /**
     * input : id & title & content
     * expect result : success
     */
    @Test
    public void updateNotice_Default_Success() throws Exception{
        //given
        Long noticeId = 123L;
        Notice baseNotice = new Notice("baseNoticeTitle", "baseNoticeContent");
        TestUtils.setEntityId(baseNotice, noticeId);
        String title = "changedNoticeTitle";
        String content = "changedNoticeContent";
        Notice noticeChangeInfo = new Notice(title, content);

        //mocking
        given(noticeRepository.findById(noticeId)).willReturn(Optional.of(baseNotice));
        
        //when
        Notice notice = noticeService.updateNotice(noticeId, noticeChangeInfo);

        //then
        assertThat(notice.getId()).isEqualTo(noticeId);
        assertThat(notice.getTitle()).isEqualTo(title);
        assertThat(notice.getContent()).isEqualTo(content);

        assertThat(baseNotice.getTitle()).isEqualTo(noticeChangeInfo.getTitle())
                .isEqualTo(title);
        assertThat(baseNotice.getContent()).isEqualTo(noticeChangeInfo.getContent())
                .isEqualTo(content);
    }
    
    /**
     * input : bad Notice id
     * expect result : NoMatchNoticeIdException
     */
    @Test
    public void updateNotice_BadNoticeId_NoMatchNoticeIdException() throws Exception{
        //given
        Long noticeId = -123L;
        String title = "changedNoticeTitle";
        String content = "changedNoticeContent";
        Notice noticeChangeInfo = new Notice(title, content);

        //mocking
        given(noticeRepository.findById(noticeId)).willReturn(Optional.empty());

        //when
        Assertions.assertThrows(NoMatchNoticeIDException.class, () -> noticeService.updateNotice(noticeId, noticeChangeInfo));

        //then
        
    }
}