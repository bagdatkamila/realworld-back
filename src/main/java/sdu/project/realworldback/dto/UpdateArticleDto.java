package sdu.project.realworldback.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateArticleDto {

    @Valid
    private ArticleDto article;

    @Data
    public static class ArticleDto {

        @NotBlank(message = "title must be filled.")
        private String title;
        @NotBlank(message = "description must be filled.")
        private String description;
        @NotBlank(message = "body must be filled.")
        private String body;

    }
}
