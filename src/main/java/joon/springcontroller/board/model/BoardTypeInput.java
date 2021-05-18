package joon.springcontroller.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardTypeInput {
    @NotBlank(message = "게시판 제목은 필수 항목입니다.")
    private String name;
}
