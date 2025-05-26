package sdu.project.realworldback.services;

import jakarta.validation.Valid;
import sdu.project.realworldback.dto.ArticleListDto;
import sdu.project.realworldback.dto.ArticleResponseDto;
import sdu.project.realworldback.dto.RegisterArticleDto;
import sdu.project.realworldback.dto.UpdateArticleDto;

public interface ArticleService {

    ArticleListDto getArticles(String tag, String author, String favorited, int offset, int limit);

    ArticleResponseDto createArticle(@Valid RegisterArticleDto dto);


    ArticleListDto getRecentArticles(int offset, int limit);

    ArticleResponseDto getArticle(String slug);

    void deleteArticle(String slug);

    ArticleResponseDto updateArticle(String slug, RegisterArticleDto dto);


    ArticleResponseDto doFavoriteArticle(String slug);
}
