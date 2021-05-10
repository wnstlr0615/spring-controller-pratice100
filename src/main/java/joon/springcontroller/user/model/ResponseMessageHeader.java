package joon.springcontroller.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResponseMessageHeader {
    private boolean result;
    private String resultCode;
    private String message;
    private int status;
}
