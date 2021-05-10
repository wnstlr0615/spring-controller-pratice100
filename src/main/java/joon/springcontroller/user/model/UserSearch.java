package joon.springcontroller.user.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserSearch {
    private String email;
    private String username;
    private String phone;

    public static UserSearch of(String email, String username, String phone){
        return UserSearch.builder()
                .email(email)
                .username(username)
                .phone(phone)
                .build();
    }
}
