package joon.springcontroller.user.exception;

public class AlreadyUserUnLockException extends RuntimeException {
    public AlreadyUserUnLockException(String message) {
        super(message);
    }
}
