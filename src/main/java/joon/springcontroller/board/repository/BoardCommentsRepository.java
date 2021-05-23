package joon.springcontroller.board.repository;

import joon.springcontroller.board.entity.BoardComment;
import joon.springcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardCommentsRepository extends JpaRepository<BoardComment, Long> {
    List<BoardComment> findAllByUser(User user);
}
