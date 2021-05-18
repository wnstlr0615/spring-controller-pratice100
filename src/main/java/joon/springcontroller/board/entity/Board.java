package joon.springcontroller.board.entity;

import joon.springcontroller.common.entity.BaseTimeEntity;
import joon.springcontroller.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Board extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_type_id")
    private BoardType boardType;

    private String  title;

    private String contents;

    private LocalDateTime regDate;

    private boolean topYn;

    private LocalDate publishStartDate;

    private LocalDate publishEndDate;


    public void updateTop() {
        topYn=true;
    }

    public void setPublish(LocalDate startDate, LocalDate endDate) {
        publishStartDate=startDate;
        publishEndDate=endDate;
    }
}
