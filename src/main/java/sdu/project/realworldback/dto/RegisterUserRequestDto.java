package sdu.project.realworldback.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import sdu.project.realworldback.validation.UniqueEmail;
import sdu.project.realworldback.validation.UniqueUsername;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequestDto {

    @Valid
    private UserData user;

    @Data
    public static class UserData {

        @UniqueUsername
        @NotBlank(message = "username must be filled.")
        private String username;

        @Email
        @UniqueEmail
        @NotBlank(message = "email must be filled.")
        private String email;

        @Length(min = 6, message = "password length must be more that 6 characters.")
        @NotBlank(message = "password must be filled.")
        private String password;
    }
}
