package joon.springcontroller.board.repository;

import joon.springcontroller.board.entity.Board;
import joon.springcontroller.board.entity.BoardHits;
import joon.springcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardHitsRepository extends JpaRepository<BoardHits, Long> {
    long countByBoardAndUser(Board board, User user);
}
