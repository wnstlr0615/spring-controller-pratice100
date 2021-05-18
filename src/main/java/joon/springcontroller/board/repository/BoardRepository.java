package joon.springcontroller.board.repository;

import joon.springcontroller.board.entity.Board;
import joon.springcontroller.board.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Long countByBoardType(BoardType boardType);
}
