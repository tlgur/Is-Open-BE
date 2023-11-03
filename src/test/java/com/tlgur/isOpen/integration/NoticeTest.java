package com.tlgur.isOpen.integration;

import com.tlgur.isOpen.controller.NoticeController;
import com.tlgur.isOpen.dto.NoticePrevContainerDTO;
import com.tlgur.isOpen.domain.Notice;
import com.tlgur.isOpen.dto.NoticePrev;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
public class NoticeTest {
    @Autowired
    private NoticeController noticeController;
    @Autowired
    private EntityManager em;
    
    @Test
    public void getRecentNotices_savedOver5Notices_Return5Prevs() throws Exception{
        //given
        Integer noticeCnt = 7;
        saveNotice(noticeCnt);
        
        //when
        NoticePrevContainerDTO recentNoticePrevsContainer = noticeController.getRecentNotices();

        //then
        //크기 검사
        assertThat(recentNoticePrevsContainer.getNoticePrevsCount()).isEqualTo(5);
        List<NoticePrev> noticePrevs = recentNoticePrevsContainer.getNoticePrevs();
        assertThat(noticePrevs).hasSize(recentNoticePrevsContainer.getNoticePrevsCount());

        //ID null 검사 + 중복 검사
        List<Long> prevIDs = noticePrevs.stream().map(NoticePrev::getNoticeId).collect(Collectors.toList());
        assertThat(prevIDs).doesNotHaveDuplicates();
        assertThat(prevIDs).doesNotContainNull();

        //title null 검사 + 중복 검사
        List<String> prevTitles = noticePrevs.stream().map(NoticePrev::getTitle).collect(Collectors.toList());
        assertThat(prevTitles).doesNotContainNull();
        assertThat(prevTitles).doesNotHaveDuplicates();
    }

    @Test
    public void getRecentNotices_savedUnder5Notices_ReturnAllNotices() throws Exception{
        //given
        Integer noticeCnt = 3;
        saveNotice(noticeCnt);

        //when
        NoticePrevContainerDTO recentNoticePrevsContainer = noticeController.getRecentNotices();

        //then
        //크기 검사
        assertThat(recentNoticePrevsContainer.getNoticePrevsCount()).isEqualTo(noticeCnt);
        List<NoticePrev> noticePrevs = recentNoticePrevsContainer.getNoticePrevs();
        assertThat(noticePrevs).hasSize(recentNoticePrevsContainer.getNoticePrevsCount());

        //ID null 검사 + 중복 검사
        List<Long> prevIDs = noticePrevs.stream().map(NoticePrev::getNoticeId).collect(Collectors.toList());
        assertThat(prevIDs).doesNotHaveDuplicates();
        assertThat(prevIDs).doesNotContainNull();

        //title null 검사 + 중복 검사
        List<String> prevTitles = noticePrevs.stream().map(NoticePrev::getTitle).collect(Collectors.toList());
        assertThat(prevTitles).doesNotContainNull();
        assertThat(prevTitles).doesNotHaveDuplicates();
    }

    @Test
    public void getRecentNotices_savedNoNotices_ReturnEmptyList() throws Exception{
        //given
//        Integer noticeCnt = 0;
//        saveNotice(noticeCnt);

        //when
        NoticePrevContainerDTO recentNoticePrevsContainer = noticeController.getRecentNotices();

        //then
        //크기 검사
        assertThat(recentNoticePrevsContainer.getNoticePrevsCount()).isEqualTo(0);
        List<NoticePrev> noticePrevs = recentNoticePrevsContainer.getNoticePrevs();
        assertThat(noticePrevs).isEmpty();
    }

    private void saveNotice(Integer noticeCnt) {
        for (int i = 1; i <= noticeCnt; i++) {
            Notice notice = new Notice("testNoticeTitle" + i, ("testNoticeContent" + i).repeat(300));
            em.persist(notice);
        }

        em.flush();
        em.clear();
    }
}
