package joon.springcontroller.user.model;

import joon.springcontroller.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserNoticeCount {
    private long id;
    private String email;
    private String userName;

    private long noticeCount;

    public static UserNoticeCount of(User user, long count) {
        return UserNoticeCount.builder()
                .id(user.getId())
                .email(user.getEmail())
                .userName(user.getUsername())
                .noticeCount(count)
                .build()
                ;
    }
}
