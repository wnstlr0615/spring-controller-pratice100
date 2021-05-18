package joon.springcontroller.common.model;

import org.springframework.http.ResponseEntity;

public class ResponseResult {
    public static ResponseEntity<?> fail(String msg){
        return ResponseEntity.badRequest().body(msg);
    }
    public static ResponseEntity<?> success(){
        return ResponseEntity.ok().build();
    }
}
