package joon.springcontroller.user.entity;

import joon.springcontroller.common.entity.BaseTimeEntity;
import joon.springcontroller.user.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    @Email
    @Column(unique = true)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private UserStatus status=UserStatus.USING;

    public boolean lockYn=false;

    public boolean isLockYn() {
        return lockYn;
    }

    public static User of(String username, String password, String email) {
        return new User(username, password, email);
    }
    public static User of(String username, String password, String email, String phone) {
        return new User(username, password, email, phone);
    }

    public User(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void updatePhone(String phone) {
        this.phone=phone;
    }

    public void updatePassword(String newPassword) {
        this.password=newPassword;
    }

    public void updateStatus(UserStatus userStatus) {
        this.status=userStatus;
    }

    public void updateUserLock() {
        this.lockYn=true;
    }

    public void updateUserUnlock() {
        this.lockYn=false;

    }
}
