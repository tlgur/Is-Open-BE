package com.tlgur.isOpen.repository;

import com.tlgur.isOpen.ProjectConfiguration;
import com.tlgur.isOpen.domain.Notice;
import com.tlgur.isOpen.domain.place.Mart;
import com.tlgur.isOpen.dto.NoticePrev;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Import(value = ProjectConfiguration.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NoticeRepositoryTest{
    @Autowired
    private EntityManager em;
    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    public void getRecent_default_findOnlyTitleAndID() throws Exception{
        //given
        Notice testNotice1 = new Notice("testNoticeTitle1", "testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 ");
        Notice testNotice2 = new Notice("testNoticeTitle2", "testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 ");
        Notice testNotice3 = new Notice("testNoticeTitle3", "testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 testNoticeContent3 ");
        Notice testNotice4 = new Notice("testNoticeTitle4", "testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 testNoticeContent4 ");
        Notice testNotice5 = new Notice("testNoticeTitle5", "testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 testNoticeContent5 ");
        Notice testNotice6 = new Notice("testNoticeTitle6", "testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 testNoticeContent6 ");

        em.persist(testNotice1);
        em.persist(testNotice2);
        em.persist(testNotice3);
        em.persist(testNotice4);
        em.persist(testNotice5);
        em.persist(testNotice6);

        em.flush();
        em.clear();

        //when
        List<NoticePrev> recentNoticePrevs = noticeRepository.getNoticePrevsRecent();

        //then
        assertThat(recentNoticePrevs).hasSize(5);
        for (NoticePrev recentNotice : recentNoticePrevs) {
            assertThat(recentNotice.getTitle()).contains("testNotice");
            assertThat(recentNotice.getNoticeId()).isNotNull();
        }
    }

    @Test
    public void getRecent_savedUnder5_returnAllNotices() throws Exception{
        //given
        Notice testNotice1 = new Notice("testNoticeTitle1", "testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 testNoticeContent1 ");
        Notice testNotice2 = new Notice("testNoticeTitle2", "testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 testNoticeContent2 ");

        em.persist(testNotice1);
        em.persist(testNotice2);

        em.flush();
        em.clear();

        //when
        List<NoticePrev> recentNoticePrevs = noticeRepository.getNoticePrevsRecent();

        //then
        assertThat(recentNoticePrevs).hasSize(2);
        for (NoticePrev recentNotice : recentNoticePrevs) {
            assertThat(recentNotice.getTitle()).contains("testNoticeTitle");
            assertThat(recentNotice.getNoticeId()).isNotNull();
        }
    }

    @Test
    public void getRecent_emptyTable_ReturnEmptyList() throws Exception{
        //given
        assertThat(noticeRepository.count()).isZero();

        //when
        List<NoticePrev> recentNoticePrevs = noticeRepository.getNoticePrevsRecent();

        //then
        assertThat(recentNoticePrevs).isEmpty();
    }
}