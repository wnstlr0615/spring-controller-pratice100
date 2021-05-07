package joon.springcontroller.notice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError {
    private String field;
    private String message;


    public static ResponseError of(FieldError e) {
        return new ResponseError(e.getField(), e.getDefaultMessage());
    }
}
