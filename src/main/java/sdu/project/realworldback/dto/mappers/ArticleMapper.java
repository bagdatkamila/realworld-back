package sdu.project.realworldback.dto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sdu.project.realworldback.dto.ArticleDto;
import sdu.project.realworldback.dto.PersonDto;
import sdu.project.realworldback.dto.RegisterArticleDto;
import sdu.project.realworldback.models.Article;
import sdu.project.realworldback.models.Person;

@Mapper(componentModel = "spring")
public interface ArticleMapper{

    @Mapping(target = "slug", source = "article.slug")
    @Mapping(target = "title", source = "article.title")
    @Mapping(target = "description", source = "article.description")
    @Mapping(target = "body", source = "article.body")
    @Mapping(target = "tagList", source = "article.tagList")
    @Mapping(target = "createdAt", source = "article.createdAt")
    @Mapping(target = "updatedAt", source = "article.updatedAt")
    @Mapping(target = "favorited", expression = "java(isFavorited(article, currentUser))")
    @Mapping(target = "favoritesCount", expression = "java(article.getFavoritedUsers().size())")
    @Mapping(target = "author", expression = "java(mapAuthor(article.getAuthor(), currentUser))")
    ArticleDto toDto(Article article, Person currentUser);


    @Mapping(target = "title", source = "article.title")
    @Mapping(target = "description", source = "article.description")
    @Mapping(target = "body", source = "article.body")
    @Mapping(target = "tagList", source = "article.tagList")
    Article toEntity(RegisterArticleDto dto);

    /**
     * Check if an article is favorited by the current user
     */
    default boolean isFavorited(Article article, Person currentUser) {
        if (currentUser == null || article.getFavoritedUsers() == null) {
            return false;
        }
        return article.getFavoritedUsers().contains(currentUser);
    }

    /**
     * Map the author to AuthorDto including the following status
     */
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
