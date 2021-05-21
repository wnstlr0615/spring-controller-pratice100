package joon.springcontroller.board.repository;

import joon.springcontroller.board.entity.BoardScrap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardScrapRepository extends JpaRepository<BoardScrap, Long> {
}
