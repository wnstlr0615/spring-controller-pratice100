package joon.springcontroller.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeInput {
    private String title;
    private String content;
    public static NoticeInput of(String title, String content){
        return new NoticeInput(title, content);
    }
}
