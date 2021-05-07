package joon.springcontroller.user.model;

import joon.springcontroller.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {
    private long id;
    private String email;
    private String username;
    protected String phone;
    public static UserResponse of(User user){
       return UserResponse.builder()
               .id(user.getId())
               .username(user.getUsername())
               .email(user.getEmail())
               .phone(user.getPhone())
               .build();
    }

}
