package joon.springcontroller.board.entity;

import joon.springcontroller.common.entity.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class BoardType extends BaseTimeEntity {
    @Id @GeneratedValue
    private long id;

    private String boardName;

    private boolean usingYn;

    public static BoardType of(String boardName) {
        return BoardType.builder()
                .boardName(boardName)
                .usingYn(true)
                .build();
    }

    public void updateName(String boardName) {
        this.boardName=boardName;
    }
}
