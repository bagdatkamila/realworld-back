package sdu.project.realworldback.controllers.person;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sdu.project.realworldback.dto.ResponseUserRequestDto;
import sdu.project.realworldback.repositories.PersonRepository;
import sdu.project.realworldback.security.JwtTokenProvider;
import sdu.project.realworldback.services.impl.PersonServiceImpl;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class PersonController {

    private final PersonServiceImpl personService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ResponseEntity<ResponseUserRequestDto> getCurrentUser(){
        String jwt = jwtTokenProvider.getCurrentJwtToken();
        return ResponseEntity.ok(personService.getCurrentUser(jwt));
    }
}
