package joon.springcontroller.board.repository;

import joon.springcontroller.board.entity.BoardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardTypeRepository extends JpaRepository<BoardType, Long> {
    Optional<BoardType> findByBoardName(String boardName);
    @Query("select bt, count(b.id) from BoardType bt" +
            " join fetch Board b on bt=b.boardType " +
            "group by bt")
    List<Object[]> findBoardTypeCount();
}
