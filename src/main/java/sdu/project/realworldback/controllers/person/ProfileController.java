package sdu.project.realworldback.controllers.person;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sdu.project.realworldback.dto.ProfileDto;
import sdu.project.realworldback.services.ProfileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/profiles/{username}")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileDto> getProfile(@PathVariable("username") String username){
        return ResponseEntity.ok(profileService.getProfile(username));
    }

    @PostMapping("/follow")
    public ResponseEntity<ProfileDto> followToUser(@PathVariable("username") String username){
        return ResponseEntity.ok(profileService.followToUser(username));
    }

    @DeleteMapping("/follow")
    public ResponseEntity<ProfileDto> unfollowFromUser(@PathVariable("username") String username){
        return ResponseEntity.ok(profileService.followToUser(username));
    }
}
