package sdu.project.realworldback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import sdu.project.realworldback.dto.PersonDto;
import sdu.project.realworldback.dto.ProfileDto;
import sdu.project.realworldback.dto.mappers.PersonMapper;
import sdu.project.realworldback.exceptions.AccessDeniedException;
import sdu.project.realworldback.exceptions.ResourceNotFoundException;
import sdu.project.realworldback.models.Person;
import sdu.project.realworldback.repositories.PersonRepository;
import sdu.project.realworldback.services.ProfileService;
import sdu.project.realworldback.services.props.PersonDetailsService;

import java.util.List;
import java.util.Set;

@Service
@Validated
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final PersonRepository profileRepository;
    private final PersonDetailsService personDetailsService;
    private final PersonMapper personMapper;

    @Override
    public ProfileDto getProfile(String username) {
        Person currentPerson = personDetailsService.getCurrentUser()
                .orElse(null);

        Person person = profileRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
        PersonDto dto = personMapper.mapAuthor(person, currentPerson);

        return ProfileDto.builder()
                .profile(dto)
                .build();
    }

    @Override
    @Transactional
    public ProfileDto followToUser(String username) {
        Person currentPerson = personDetailsService.getCurrentUser()
                .orElseThrow(() -> new AccessDeniedException("Unauthenticated."));

        Person person = profileRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        if (person.equals(currentPerson)) {
            throw new IllegalArgumentException("You cannot follow yourself.");
        }

        Set<Person> followers = person.getFollowers();

        if (followers.contains(currentPerson)){
            followers.remove(currentPerson);
        }else
            followers.add(currentPerson);

        person.setFollowers(followers);
        profileRepository.save(person);

        PersonDto dto = personMapper.mapAuthor(person, currentPerson);

        return ProfileDto.builder()
                .profile(dto)
                .build();
    }


}
