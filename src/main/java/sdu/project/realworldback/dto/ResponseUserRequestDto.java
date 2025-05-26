package sdu.project.realworldback.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserRequestDto {

    private UserData user;

    @Data
    @Builder
    public static class UserData {
        private String email;
        private String token;
        private String username;
        private String bio;
        private String image;
    }
}
