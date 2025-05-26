package sdu.project.realworldback.services;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import sdu.project.realworldback.dto.LoginUserDto;
import sdu.project.realworldback.dto.ResponseUserRequestDto;
import sdu.project.realworldback.dto.RegisterUserRequestDto;
import sdu.project.realworldback.models.Person;

public interface PersonService {

    ResponseUserRequestDto createUser(@Valid RegisterUserRequestDto dto);
    Person getPersonByUsername(String username);
    ResponseUserRequestDto loginUser(@Valid LoginUserDto dto);

    ResponseUserRequestDto getCurrentUser(String jwt);
}
