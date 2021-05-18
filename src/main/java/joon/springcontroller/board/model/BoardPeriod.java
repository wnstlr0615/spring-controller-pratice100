package joon.springcontroller.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BoardPeriod {
    private LocalDate startDate;
    private LocalDate endDate;

    public static BoardPeriod of(LocalDate startDate, LocalDate endDate) {
        return BoardPeriod.builder()
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
