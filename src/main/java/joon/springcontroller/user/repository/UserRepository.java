package joon.springcontroller.user.repository;

import joon.springcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameAndPhone(String userName, String phone);
}
