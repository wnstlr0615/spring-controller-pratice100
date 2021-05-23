package joon.springcontroller.board.repository;

import joon.springcontroller.board.entity.BoardBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardBookMarkRepository extends JpaRepository<BoardBookmark, Long> {
}
