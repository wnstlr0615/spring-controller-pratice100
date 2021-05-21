package joon.springcontroller.board.repository;

import joon.springcontroller.board.entity.Board;
import joon.springcontroller.board.entity.BoardLike;
import joon.springcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    long countByUserAndBoard(User user, Board board);

    Optional<BoardLike> findByUserAndBoard(User user, Board board);
}
