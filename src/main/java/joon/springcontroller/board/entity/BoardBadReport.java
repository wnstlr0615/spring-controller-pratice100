package joon.springcontroller.board.entity;

import joon.springcontroller.common.entity.BaseTimeEntity;
import joon.springcontroller.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardBadReport extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "board_bad_report_id")
    private Long id;

    //신고자정보
    @Column private long userId;
    @Column private String userName;
    @Column private String userEmail;

    //신고게시글정보
    @Column private long boardId;
    @Column private long boardUserId;
    @Column private String boardTitle;
    @Column private String boardContents;
    @Column private LocalDateTime boardRegDate;


    private String comment;

    public static BoardBadReport of(User user, Board board, String comment) {
        return BoardBadReport.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .userEmail(user.getEmail())
                .boardId(board.getId())
                .boardUserId(board.getUser().getId())
                .boardTitle(board.getTitle())
                .boardContents(board.getContents())
                .boardRegDate(board.getCreateDate())
                .comment(comment)
                .build();
    }
}
