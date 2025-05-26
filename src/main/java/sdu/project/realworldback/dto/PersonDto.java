package sdu.project.realworldback.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    @NotBlank(message = "Username must be filled")
    private String username;
    @NotBlank(message = "bio must be filled.")
    private String bio;
    private String image;
    private boolean following;
}
