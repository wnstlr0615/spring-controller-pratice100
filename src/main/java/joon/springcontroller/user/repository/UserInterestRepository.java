package joon.springcontroller.user.repository;

import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.entity.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
    long countByUserAndInterestUser(User user, User interestUser);
}
