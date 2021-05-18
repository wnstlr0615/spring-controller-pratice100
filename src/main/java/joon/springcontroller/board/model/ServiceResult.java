package joon.springcontroller.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResult {
    private boolean result;
    private String message;

    public static ServiceResult fail(String message){
        return ServiceResult.builder()
                .result(false)
                .message(message)
                .build();
    }
    public static ServiceResult success(){
        return ServiceResult.builder()
                .result(true)
                .build();
    }
}
