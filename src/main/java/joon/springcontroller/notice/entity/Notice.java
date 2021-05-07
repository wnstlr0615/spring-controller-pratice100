package joon.springcontroller.notice.entity;

import joon.springcontroller.notice.model.NoticeInput;
import joon.springcontroller.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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

    @NotEmpty(message = "제목은 필수 입니다.")
    @Size(min = 0, max = 50, message = "글자수는 50자 이하여야 합니다")
    private String title;

    @NotEmpty(message = "내용은 필수 입니다.")
    @Size(min = 0, max = 500, message = "글자수는 500자 이하여야 합니다")
    private String content;

    private int likes;
    private int views;

    private LocalDate createDate;
    private LocalDateTime lastModifiedDate;

    private boolean deleted;
    private LocalDateTime deletedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Notice(String title, String content, LocalDate createDate, User user) {
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.user = user;
    }

    public static Notice of(Long id, String title, String content, LocalDate createDate) {
        return new Notice(id, title, content, createDate);
    }
    public static Notice of( String title, String content, LocalDate createDate, User user) {
        return new Notice(title, content, createDate, user);
    }

    public static Notice of( String title, String content, LocalDate createDate) {
        return new Notice(title, content, createDate);
    }
    public static Notice of( String title, String content) {
        return new Notice(title, content, LocalDate.now());
    }
    public static Notice create(NoticeInput noticeInput) {
        return new Notice(noticeInput.getTitle(), noticeInput.getContent(), LocalDate.now());
    }
    public Notice(Long id, String title, String content, LocalDate createDate) {
        this(title,content,createDate);
        this.id = id;
    }

    public Notice(String title, String content, LocalDate createDate) {
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.lastModifiedDate=LocalDateTime.now();
        this.deleted=false;
        this.likes=0;
        this.views=0;
    }

    public Notice(String title, String content) {
        this(title,content,LocalDate.now());
    }

    public void updateTitleAndContent(NoticeInput noticeInput) {
        if(noticeInput.getTitle()!=null){
            this.title=noticeInput.getTitle();
        }
        if(noticeInput.getContent()!=null){
            this.content=noticeInput.getContent();
        }
        this.lastModifiedDate=LocalDateTime.now();
    }

    public void addView() {
        views=views+1;
    }

    public void deleted() {
        deleted=true;
        deletedDate=LocalDateTime.now();
    }
}
