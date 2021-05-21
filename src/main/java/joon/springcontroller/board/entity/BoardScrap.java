package joon.springcontroller.board.entity;

import joon.springcontroller.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class BoardScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_scrap_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //스크립글정보
    @Column private long boardId;
    @Column private long boardTypeId;
    @Column private long boardUserId;
    @Column private String boardTitle;
    @Column private String boardContents;
    @Column private LocalDateTime boardRegDate;

    @Column private LocalDateTime regDate;

    public static BoardScrap of(User user, Board board){
        return BoardScrap.builder()
                .user(user)
                .boardId(board.getId())
                .boardUserId(board.getUser().getId())
                .boardTitle(board.getTitle())
                .boardContents(board.getContents())
                .boardRegDate(board.getCreateDate())
                .build();
    }
}
