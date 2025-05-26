package sdu.project.realworldback.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sdu.project.realworldback.dto.PersonDto;
import sdu.project.realworldback.dto.RegisterUserRequestDto;
import sdu.project.realworldback.dto.ResponseUserRequestDto;
import sdu.project.realworldback.models.Article;
import sdu.project.realworldback.models.Person;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(source = "email", target = "email")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "bio", target = "bio")
    @Mapping(source = "image", target = "image")
    ResponseUserRequestDto.UserData toUserData(Person person);

    default ResponseUserRequestDto toResponse(Person person) {
        return new ResponseUserRequestDto(toUserData(person));
    }

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    Person toEntity(RegisterUserRequestDto.UserData userData);


    default boolean isFavorited(Article article, Person currentUser) {
        if (currentUser == null || article.getFavoritedUsers() == null) {
            return false;
        }
        return article.getFavoritedUsers().contains(currentUser);
    }

    default PersonDto mapAuthor(Person author, Person currentUser) {
        if (author == null) {
            return null;
        }

        boolean following = false;
        if (currentUser != null && author.getFollowers() != null) {
            following = author.getFollowers().contains(currentUser);
        }

        return PersonDto.builder()
                .username(author.getUsername())
                .bio(author.getBio())
                .image(author.getImage())
                .following(following)
                .build();
    }
}
