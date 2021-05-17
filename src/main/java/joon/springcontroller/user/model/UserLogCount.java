package joon.springcontroller.user.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class UserLogCount {
    private Long id;
    private String email;
    private String userName;

    private Long noticeCount;
    private Long noticeLikeCount;

    public UserLogCount(Long id, String email, String userName, Long noticeCount, Long noticeLikeCount) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.noticeCount = noticeCount;
        this.noticeLikeCount = noticeLikeCount;
    }
}
