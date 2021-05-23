package joon.springcontroller.user.entity;

import joon.springcontroller.user.model.UserPointInput;
import joon.springcontroller.user.model.UserPointType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column
    private UserPointType userPointType;

    @Column
    private int point;

    public static UserPoint of(User user, UserPointInput userPointInput) {
        return UserPoint.builder()
                .user(user)
                .userPointType(userPointInput.getPointType())
                .point((userPointInput.getPointType().getValue()))
                .build();
    }
}
