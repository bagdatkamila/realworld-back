package sdu.project.realworldback.services.props;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sdu.project.realworldback.exceptions.ResourceNotFoundException;
import sdu.project.realworldback.models.Person;
import sdu.project.realworldback.repositories.PersonRepository;
import sdu.project.realworldback.security.PersonDetails;

import java.util.Optional;

@Data
@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Person with username: " + username + " not found."));

        return new PersonDetails(person);
    }

    public Optional<Person> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof PersonDetails){
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            return personRepository.findById(personDetails.getPerson().getId());
        }
        return Optional.empty();
    }
}
