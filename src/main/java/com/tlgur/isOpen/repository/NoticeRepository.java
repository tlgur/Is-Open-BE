package com.tlgur.isOpen.repository;

import com.tlgur.isOpen.domain.Notice;
import com.tlgur.isOpen.dto.NoticePrev;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {

    @Query("select new com.tlgur.isOpen.dto.NoticePrev(n.id, n.title) from Notice n order by n.createdAt desc limit 5")
    public List<NoticePrev> getNoticePrevsRecent();
}
