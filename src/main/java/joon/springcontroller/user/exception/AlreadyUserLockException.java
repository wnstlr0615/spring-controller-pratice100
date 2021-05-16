package joon.springcontroller.user.exception;

public class AlreadyUserLockException extends RuntimeException {
    public AlreadyUserLockException(String message) {
        super(message);
    }
}
