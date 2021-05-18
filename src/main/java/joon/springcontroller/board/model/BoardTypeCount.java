package joon.springcontroller.board.model;

import joon.springcontroller.board.entity.BoardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardTypeCount {
    private long id;
    private String boardName;
    private LocalDateTime regDate;
    private boolean usingYn;
    private long boardCount;

    public static BoardTypeCount of(Object[] o) {
    return BoardTypeCount.builder()
            .id(((BoardType)o[0]).getId())
            .boardName(((BoardType)o[0]).getBoardName())
            .regDate(((BoardType)o[0]).getCreateDate())
            .usingYn(((BoardType)o[0]).isUsingYn())
            .boardCount((long)o[1])
            .build();
    }
}
