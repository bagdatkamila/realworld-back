package sdu.project.realworldback.dto.mappers;

import org.mapstruct.Mapper;
import sdu.project.realworldback.dto.CommentDto;
import sdu.project.realworldback.dto.PersonDto;
import sdu.project.realworldback.models.Comment;
import sdu.project.realworldback.models.Person;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    default List<CommentDto> toDtoList(List<Comment> comments, Person currentPerson) {
        if (comments == null) return Collections.emptyList();
        return comments.stream()
                .map(comment -> toDto(comment, currentPerson))
                .collect(Collectors.toList());
    }

    default CommentDto toDto(Comment comment, Person currentPerson) {
        if (comment == null) return null;

        return new CommentDto(
                comment.getId(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getBody(),
                mapAuthor(comment.getAuthor(), currentPerson)
        );
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
