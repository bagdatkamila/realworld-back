package sdu.project.realworldback.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDto {

    @Valid
    private PersonDto user;

    @Data
    public static class PersonDto{
        @Email
        @NotBlank(message = "email must be filled.")
        private String email;

        @NotBlank(message = "password must be filled.")
        private String password;
    }
}
