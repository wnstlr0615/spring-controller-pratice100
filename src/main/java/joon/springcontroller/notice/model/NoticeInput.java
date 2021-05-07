package joon.springcontroller.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeInput {

    @NotEmpty(message = "제목은 필수 입니다.")
    @Size(min = 10, max = 100, message = "글자수는 50자 이하여야 합니다")
    private String title;

    @NotEmpty(message = "내용은 필수 입니다.")
    @Size(min = 50, max = 1000, message = "글자수는 500자 이하여야 합니다")
    private String content;

    public static NoticeInput of(String title, String content){
        return new NoticeInput(title, content);
    }

}
