package joon.springcontroller.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLogin {
    @NotBlank(message = "이메일 항목은 필수 입니다.")
    private String email;

    @NotBlank(message = "비밀번호 항목은 필수 입니다.")
    private String password;

    public static UserLogin of(String email, String password) {
        return new UserLogin(email, password);
    }
}
