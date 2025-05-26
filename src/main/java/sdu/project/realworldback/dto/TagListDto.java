package sdu.project.realworldback.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagListDto {

    private List<String> tags;
}
