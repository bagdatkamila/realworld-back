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
        if (username == null) {
            return false;
        }

        String normalizedUsername = username.trim().toLowerCase();

        try {
            return !personRepository.existsByUsername(normalizedUsername);
        } catch (Exception e) {
            return false;
        }
    }

}
