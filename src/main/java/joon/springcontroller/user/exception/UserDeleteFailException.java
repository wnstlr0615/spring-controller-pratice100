package joon.springcontroller.user.exception;

public class UserDeleteFailException extends RuntimeException {
    public UserDeleteFailException(String message) {
        super(message);
    }
}
