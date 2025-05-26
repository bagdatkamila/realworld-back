package sdu.project.realworldback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sdu.project.realworldback.models.Person;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByUsername(String username);

    Optional<Person> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
