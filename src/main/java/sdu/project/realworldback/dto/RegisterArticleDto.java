package sdu.project.realworldback.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterArticleDto {

    @Valid
    private ArticleDto article;

    @Data
    public static class ArticleDto{

        @NotBlank(message = "title must be filled.")
        private String title;
        @NotBlank(message = "description must be filled.")
        private String description;
        @NotBlank(message = "body must be filled.")
        private String body;
        @NotNull(message = "tagList must be not null.")
        @Size(min = 1, message = "tagList must contain at least one tag.")
        private Set<String> tagList;
    }
}
