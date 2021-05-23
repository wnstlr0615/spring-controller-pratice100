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
public class BoardBookmark {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column private long boardId;
    @Column private long boardTypeId;
    @Column private String boardTitle;
    @Column private String boardUrl;

    @Column private LocalDateTime regDate;
    public static BoardBookmark of(User user, Board board){
        return BoardBookmark.builder()
                .user(user)
                .boardId(board.getId())
                .boardTypeId(board.getBoardType().getId())
                .boardTitle(board.getTitle())
                .boardUrl(board.getUrl())
                .build();
    }
}
