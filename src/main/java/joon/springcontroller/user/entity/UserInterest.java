package joon.springcontroller.user.entity;

import joon.springcontroller.common.entity.BaseTimeEntity;
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
public class UserInterest extends BaseTimeEntity {
    @Id @GeneratedValue
    @Column(name = "user_interest_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User interestUser;

    public static UserInterest of(User user, User interestUser) {
        return UserInterest.builder()
                .user(user)
                .interestUser(interestUser)
                .build();
    }
}
