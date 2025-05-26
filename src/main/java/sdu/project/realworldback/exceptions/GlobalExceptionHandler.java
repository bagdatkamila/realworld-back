package sdu.project.realworldback.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex){

        return ErrorResponse.builder()
                .errors(ErrorResponse.ErrorBody.builder()
                        .body(List.of(ex.getMessage()))
                        .build())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        return ErrorResponse.builder()
                .errors(ErrorResponse.ErrorBody.builder()
                        .body(ex.getFieldErrors().stream()
                                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                                .toList())
                        .build())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException ex){
        return ErrorResponse.builder()
                .errors(ErrorResponse.ErrorBody.builder()
                        .body(List.of(ex.getMessage()))
                        .build())
                .build();
    }


    @ExceptionHandler(ForbiddenEventException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbiddenEventException(ForbiddenEventException ex){
        return ErrorResponse.builder()
                .errors(ErrorResponse.ErrorBody.builder()
                        .body(List.of(ex.getMessage()))
                        .build())
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex){
        return ErrorResponse.builder()
                .errors(ErrorResponse.ErrorBody.builder()
                        .body(List.of(ex.getMessage()))
                        .build())
                .build();
    }
}
