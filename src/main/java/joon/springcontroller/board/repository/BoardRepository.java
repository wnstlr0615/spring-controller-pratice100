package joon.springcontroller.board.repository;

import joon.springcontroller.board.entity.Board;
import joon.springcontroller.board.entity.BoardType;
import joon.springcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Long countByBoardType(BoardType boardType);

    List<Board> findAllByUser(User user);
}
