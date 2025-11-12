package sdu.project.realworldback.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import sdu.project.realworldback.dto.ArticleResponseDto;
import sdu.project.realworldback.services.ArticleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles/{slug}/favorite")
public class FavoriteController {

    private final ArticleService articleService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<ArticleResponseDto> makeFavoriteArticle(@PathVariable("slug") String slug){
        return ResponseEntity.ok(articleService.doFavoriteArticle(slug));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    public ResponseEntity<ArticleResponseDto> unfavoriteArticle(@PathVariable("slug") String slug){
        return ResponseEntity.ok(articleService.doFavoriteArticle(slug));
    }
}
