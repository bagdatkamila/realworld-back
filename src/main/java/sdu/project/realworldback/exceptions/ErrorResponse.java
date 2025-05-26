package sdu.project.realworldback.exceptions;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private ErrorBody errors;

    @Data
    @Builder
    public static class ErrorBody{
        List<String> body;
    }
}
