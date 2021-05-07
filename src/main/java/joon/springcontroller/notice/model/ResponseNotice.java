package joon.springcontroller.notice.model;

import joon.springcontroller.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseNotice {

    private Long id;

    private String title;

    private String content;

    private int likes;
    private int views;

    private LocalDate createDate;
    private LocalDateTime lastModifiedDate;
    public  static ResponseNotice of(Notice notice){
        return ResponseNotice.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createDate(notice.getCreateDate())
                .lastModifiedDate(notice.getLastModifiedDate())
                .views(notice.getViews())
                .likes(notice.getLikes())
                .build();
    }
}
