package sdu.project.realworldback.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleResponseDto {

    private ArticleDto article;
}
