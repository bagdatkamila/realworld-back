package sdu.project.realworldback.exceptions;

public class ForbiddenEventException extends RuntimeException {
    public ForbiddenEventException(String message) {
        super(message);
    }
}
