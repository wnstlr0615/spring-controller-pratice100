package joon.springcontroller.board.repository;

import joon.springcontroller.board.entity.BoardBadReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardBadReportRepository extends JpaRepository<BoardBadReport, Long> {
}
