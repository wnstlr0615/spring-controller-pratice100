package joon.springcontroller.user.model;

import joon.springcontroller.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResponseMessage2<T>{
    private Long totalCount;
    private T data;

    public static ResponseMessage2<List<User>> of(long totalUserCount, List<User> userAllList) {
        return new ResponseMessage2<List<User>>(totalUserCount, userAllList);
    }
}
