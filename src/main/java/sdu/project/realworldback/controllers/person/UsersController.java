package sdu.project.realworldback.controllers.person;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sdu.project.realworldback.dto.LoginUserDto;
import sdu.project.realworldback.dto.ResponseUserRequestDto;
import sdu.project.realworldback.dto.RegisterUserRequestDto;
import sdu.project.realworldback.services.impl.PersonServiceImpl;

@Validated
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UsersController {

    private final PersonServiceImpl personService;

    @PostMapping
    public ResponseEntity<ResponseUserRequestDto> createUser(@RequestBody @Valid RegisterUserRequestDto dto){
        return ResponseEntity.ok(personService.createUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseUserRequestDto> loginUser(@RequestBody @Valid LoginUserDto dto){

        return ResponseEntity.ok(personService.loginUser(dto));
    }
}
