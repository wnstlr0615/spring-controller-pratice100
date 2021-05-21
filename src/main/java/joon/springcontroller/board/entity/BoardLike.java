package joon.springcontroller.board.entity;

import joon.springcontroller.common.entity.BaseTimeEntity;
import joon.springcontroller.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardLike extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "board_like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public static BoardLike of(User user, Board board) {
        return BoardLike.builder()
                .user(user)
                .board(board)
                .build();
    }
}