package joon.springcontroller.notice.exception;

public class AlreadyDeletedException extends RuntimeException {
    public AlreadyDeletedException(String msg) {
        super(msg);
    }
}
