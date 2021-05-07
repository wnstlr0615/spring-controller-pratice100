package joon.springcontroller.user.repository;

import joon.springcontroller.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
