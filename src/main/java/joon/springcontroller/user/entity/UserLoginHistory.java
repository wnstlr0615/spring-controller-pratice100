package joon.springcontroller.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserLoginHistory {
    @Id
    @GeneratedValue
    @Column(name = "user_login_history_id")
    private Long id;

    private Long userId;

    private String email;

    private String username;

    private LocalDateTime loginDate;

    private String ipAddr;

    public UserLoginHistory(Long userId, String email, String username, LocalDateTime loginDate, String ipAddr) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.loginDate = loginDate;
        this.ipAddr = ipAddr;
    }

    public static UserLoginHistory of(User findUser) {
        return new UserLoginHistory(findUser.getId(), findUser.getEmail(), findUser.getUsername(), LocalDateTime.now(), "생략");
    }
}
