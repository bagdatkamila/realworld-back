package sdu.project.realworldback.services.impl;

import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sdu.project.realworldback.dto.LoginUserDto;
import sdu.project.realworldback.dto.ResponseUserRequestDto;
import sdu.project.realworldback.dto.RegisterUserRequestDto;
import sdu.project.realworldback.dto.mappers.PersonMapper;
import sdu.project.realworldback.exceptions.AccessDeniedException;
import sdu.project.realworldback.exceptions.ResourceNotFoundException;
import sdu.project.realworldback.models.Person;
import sdu.project.realworldback.repositories.PersonRepository;
import sdu.project.realworldback.security.JwtTokenProvider;
import sdu.project.realworldback.services.PersonService;
import sdu.project.realworldback.services.props.PersonDetailsService;


@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final PersonDetailsService personDetailsService;

    @Override
    public ResponseUserRequestDto createUser(RegisterUserRequestDto dto) {


        Person person = personMapper.toEntity(dto.getUser());
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setImage("http://localhost:9000/images/default.png");
        person = personRepository.save(person);
        String token = tokenProvider
                .accessToken(
                        person.getId(),
                        person.getUsername(),
                        Collections.nullSafe(person.getRoles())
                );
        person.setToken(token);

        return personMapper.toResponse(person);
    }

    @Override
    public Person getPersonByUsername(String username) {
        return personRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Person with username: " + username + " not found."));
    }

    @Override
    public ResponseUserRequestDto loginUser(LoginUserDto dto) {

        Person person = personRepository.findByEmail(dto.getUser().getEmail())
                .orElseThrow(() -> new AccessDeniedException(
                        "email or password are incorrect."));

        if (!passwordEncoder.matches(dto.getUser().getPassword(), person.getPassword()))
            throw new AccessDeniedException("email or password are incorrect.");

        String token = tokenProvider
                .accessToken(
                        person.getId(),
                        person.getUsername(),
                        Collections.nullSafe(person.getRoles())
                );
        person.setToken(token);


        return personMapper.toResponse(person);
    }

    @Override
    public ResponseUserRequestDto getCurrentUser(String jwt) {

        Person person = personDetailsService.getCurrentUser()
                .orElseThrow(() -> new AccessDeniedException("Unauthenticated."));
        person.setToken(jwt);

        return personMapper.toResponse(person);
    }


}
