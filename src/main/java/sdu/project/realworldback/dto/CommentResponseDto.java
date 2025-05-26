package sdu.project.realworldback.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponseDto {

    private CommentDto comment;
}
