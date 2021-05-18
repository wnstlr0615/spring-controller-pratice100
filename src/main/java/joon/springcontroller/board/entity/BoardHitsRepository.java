package joon.springcontroller.board.entity;

import joon.springcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardHitsRepository extends JpaRepository<BoardHits, Long> {
    long countByBoardAndUser(Board board, User user);
}
