package joon.springcontroller.user.repository;

import joon.springcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndPhone(String userName, String phone);
    @Query("select u from User  as u  where  u.createDate between :startDate and :endDate ")
    List<User> findAllToday(@Param("startDate") LocalDateTime startDateTime, @Param("endDate") LocalDateTime endDateTime);
}
