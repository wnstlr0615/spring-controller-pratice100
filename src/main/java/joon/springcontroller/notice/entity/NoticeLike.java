package joon.springcontroller.notice.entity;

import joon.springcontroller.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NoticeLike {
    @Id
    @GeneratedValue
    @Column(name = "notice_like_id")
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Notice notice;

    public NoticeLike(User user, Notice notice) {
        this.user = user;
        this.notice = notice;
    }

    public static NoticeLike of(User user, Notice notice) {
        return new NoticeLike(user, notice);
    }
}
