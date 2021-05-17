package joon.springcontroller.notice.repository;

import joon.springcontroller.notice.entity.Notice;
import joon.springcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<List<Notice>> findByIdIn(List<Long> id);

    List<Notice> findByTitle(String title);

    List<Notice> findByUser(User user);
    @Query(value = "select u, count(n) from Notice n " +
            " join fetch  User u " +
            " on n.user=u " +
            "group by u")
    List<Object[]> findAllWithUser();//new joon.springcontroller.user.entity.User.UserLogCount()
    @Query(value =  " select u.user_id, u.email, u.username " +
            " , (select count(*) from notice n where n.user_id = u.id) as  notice_count " +
            " , (select count(*) from notice_like nl where nl.user_id = u.id) as notice_like_count " +
            " from user u " , nativeQuery = true)
    List<Object[]> findUserLogCount();
}

//사용자별 게시글 갯수
//사용자별 좋아요 갯수
