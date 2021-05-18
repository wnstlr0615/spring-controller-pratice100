package joon.springcontroller.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardTypeUsing {
    private boolean usingYn;

    public static BoardTypeUsing of(boolean using) {
        return BoardTypeUsing.builder()
                .usingYn(using)
                .build();
    }
}
