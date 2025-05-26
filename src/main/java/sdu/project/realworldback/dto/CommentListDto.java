package sdu.project.realworldback.dto;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CommentListDto {

    @Valid
    private List<CommentDto> comments;

}
