package joon.springcontroller.notice.repository;

import joon.springcontroller.notice.entity.NoticeLike;
import joon.springcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeLikeRepository extends JpaRepository<NoticeLike, Long> {
    List<NoticeLike> findByUser(User findUser);
}
