package sdu.project.realworldback.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import sdu.project.realworldback.models.Person;

@Data
public class PersonDetails implements UserDetails {

    private final Person person;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return  person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getUsername();
    }

}
