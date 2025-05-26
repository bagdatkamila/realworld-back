package sdu.project.realworldback.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sdu.project.realworldback.dto.*;
import sdu.project.realworldback.dto.mappers.ArticleMapper;
import sdu.project.realworldback.exceptions.AccessDeniedException;
import sdu.project.realworldback.exceptions.ForbiddenEventException;
import sdu.project.realworldback.exceptions.ResourceNotFoundException;
import sdu.project.realworldback.models.Article;
import sdu.project.realworldback.models.Person;
import sdu.project.realworldback.repositories.ArticleRepository;
import sdu.project.realworldback.repositories.FavoriteRepository;
import sdu.project.realworldback.services.ArticleService;
import sdu.project.realworldback.services.props.PersonDetailsService;
import sdu.project.realworldback.utill.Util;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final FavoriteRepository favoriteRepository;
    private final PersonDetailsService personDetailsService;

    private final ArticleMapper articleMapper;
    private final Util util;

    @Override
    public ArticleListDto getArticles(String tag, String author, String favorited, int offset, int limit) {

        Pageable pageable = PageRequest.of(offset/limit, limit);
        List<Article> articles;

        Person currentPerson = personDetailsService.getCurrentUser().orElse(null);

        if (tag != null && !tag.isBlank()){

            articles = articleRepository.findByTagListContaining(tag, pageable);

        } else if (author != null && !author.isBlank()) {

            articles = articleRepository.findByAuthorUsername(author, pageable);
        }else if (favorited != null && !favorited.isBlank()){

            articles = favoriteRepository.findAllByFavoritedByUsername(favorited, pageable);
        }
        else{

            articles = articleRepository.findAll();
        }

        List<ArticleDto> articleDtos = articles.stream()
                .map(article ->  articleMapper.toDto(article, currentPerson))
                .toList();

        return ArticleListDto.builder()
                .articles(articleDtos)
                .articlesCount(articleDtos.size())
                .build();
    }

    @Override
    @Transactional
    public ArticleResponseDto createArticle(RegisterArticleDto dto) {

        Person author = personDetailsService.getCurrentUser()
                .orElseThrow(() -> new AccessDeniedException("Unauthenticated."));
        Article article = articleMapper.toEntity(dto);

        String slug = util.toSlug(article.getTitle());
//        TODO need implement logic of creating unique slug
        article.setSlug(slug);
        article.setAuthor(author);
        ArticleDto articleDto = articleMapper.toDto(article, author);

        articleRepository.save(article);

        return ArticleResponseDto.builder()
                .article(articleDto)
                .build();
    }

    @Override
    public ArticleListDto getRecentArticles(int offset, int limit) {

        Person person = personDetailsService.getCurrentUser()
                .orElseThrow(() -> new AccessDeniedException("Unauthenticated."));

        log.info("Person got {}", person.getId());
        Pageable pageable = PageRequest.of(offset/limit, limit);

        List<Article> articles = articleRepository.findAllArticlesByFollowers(person.getId(), pageable);

        List<ArticleDto> articleDtos = articles.stream()
                .map(article ->  articleMapper.toDto(article, person))
                .toList();

        return ArticleListDto.builder()
                .articles(articleDtos)
                .articlesCount(articleDtos.size())
                .build();
    }

    @Override
    public ArticleResponseDto getArticle(String slug) {
        Person currentPerson = personDetailsService.getCurrentUser()
                .orElse(null);

        Article article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article with slug: " + slug + " not found."));

        ArticleDto articleDto = articleMapper.toDto(article, currentPerson);

        return ArticleResponseDto.builder()
                .article(articleDto)
                .build();
    }

    @Override
    @Transactional
    public void deleteArticle(String slug) {
//    TODO need to add role based authorization feature
        Person currentPerson = personDetailsService.getCurrentUser()
                        .orElseThrow(() -> new AccessDeniedException("Unauthenticated"));

        Article article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article with slug: " + slug + " not found."));

        if (!currentPerson.equals(article.getAuthor()))
            throw new ForbiddenEventException("Forbidden");

        articleRepository.delete(article);
    }

    @Override
    @Transactional
    public ArticleResponseDto updateArticle(String slug, RegisterArticleDto dto) {
        Article article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article with slug: " + slug + " not found."));

        RegisterArticleDto.ArticleDto articleDto = dto.getArticle();

        article.setSlug(util.toSlug(articleDto.getTitle()));
        article.setTitle(articleDto.getTitle());
        article.setDescription(articleDto.getDescription());
        article.setBody(articleDto.getBody());
        article.setTagList(articleDto.getTagList());

        articleRepository.save(article);

        ArticleDto genDto = articleMapper.toDto(article, article.getAuthor());

        return ArticleResponseDto.builder()
                .article(genDto)
                .build();
    }

    @Override
    @Transactional
    public ArticleResponseDto doFavoriteArticle(String slug) {

        Person currentPerson = personDetailsService.getCurrentUser()
                .orElseThrow(() -> new AccessDeniedException("Unauthenticated."));

        Article article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article with slug: " + slug + " not found."));

        Set<Person> personSet = article.getFavoritedUsers();

        if (personSet.contains(currentPerson))
            personSet.remove(currentPerson);
        else
            personSet.add(currentPerson);

        articleRepository.save(article);
        ArticleDto articleDto = articleMapper.toDto(article, currentPerson);

        return ArticleResponseDto.builder()
                .article(articleDto)
                .build();
    }
}
