package joon.springcontroller.notice.service;

import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<List<Notice>> findByIdIn(List<Long> id);

    List<Notice> findByTitle(String title);

    List<Notice> findByUser(User user);
}
