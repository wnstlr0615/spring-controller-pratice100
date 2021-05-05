package joon.springcontroller.notice.entity;

import joon.springcontroller.notice.model.NoticeInput;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    private String title;

    private String content;


    private LocalDate createDate;

    private int likes;

    private int views;

    public Notice(Long id, String title, String content, LocalDate createDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        likes=0;
        views=0;
    }

    public static Notice of(Long id, String title, String content, LocalDate createDate) {
        return new Notice(id, title, content, createDate);
    }

    public static Notice create(NoticeInput noticeInput) {
        return new Notice(noticeInput.getTitle(), noticeInput.getContent(), LocalDate.now());
    }

    public Notice(String title, String content, LocalDate createDate) {
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        likes=0;
        views=0;
    }

    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
        likes=0;
        views=0;
    }

}
