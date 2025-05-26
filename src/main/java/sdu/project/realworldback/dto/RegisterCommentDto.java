package sdu.project.realworldback.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterCommentDto {

    @Valid
    private CommentDto comment;

    @Data
    public static class CommentDto{
        @NotBlank(message = "Comment must be filled.")
        private String body;
    }
}
