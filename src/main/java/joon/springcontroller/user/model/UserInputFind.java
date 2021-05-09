package joon.springcontroller.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInputFind {
    private String userName;
    private String phone;

    public static UserInputFind of(String username, String phone) {
        return new UserInputFind(username, phone);
    }
}
