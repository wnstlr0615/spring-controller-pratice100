package joon.springcontroller.log.repository;

import joon.springcontroller.log.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsRepository extends JpaRepository<Logs, Long> {
}
