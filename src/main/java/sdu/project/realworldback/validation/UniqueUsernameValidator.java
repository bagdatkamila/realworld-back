package sdu.project.realworldback.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sdu.project.realworldback.repositories.PersonRepository;

@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final PersonRepository personRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return username != null && !personRepository.existsByUsername(username);
    }
}
