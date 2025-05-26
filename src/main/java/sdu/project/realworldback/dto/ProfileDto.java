package sdu.project.realworldback.dto;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileDto {

    @Valid
    private PersonDto profile;
}
