package joon.springcontroller.common.model;

import joon.springcontroller.board.model.ServiceResult;
import joon.springcontroller.user.model.ResponseMessage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseResult {
    public static ResponseEntity<?> fail(String msg){
        return ResponseEntity.badRequest().body(msg);
    }
    public static ResponseEntity<?> success(){
        return ResponseEntity.ok().build();
    }
    public static ResponseEntity<?> success(Object o){
        return ResponseEntity.ok().body(ResponseMessage.success(o));
    }
    public static ResponseEntity<?> result(ServiceResult result){
        if(result.isResult()){
            return fail(result.getMessage());
        }
        return success();
    }

    public static ResponseEntity<?> fail(String message, List<ResponseError> errorList) {
        return ResponseEntity.badRequest().body(errorList);
    }
}
