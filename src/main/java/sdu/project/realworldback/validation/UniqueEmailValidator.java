package sdu.project.realworldback.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sdu.project.realworldback.repositories.PersonRepository;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final PersonRepository personRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && !personRepository.existsByEmail(email);
    }
}
