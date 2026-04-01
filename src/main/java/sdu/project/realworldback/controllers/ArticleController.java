package sdu.project.realworldback.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import sdu.project.realworldback.dto.ArticleListDto;
import sdu.project.realworldback.dto.ArticleResponseDto;
import sdu.project.realworldback.dto.RegisterArticleDto;
import sdu.project.realworldback.services.ArticleService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public ResponseEntity<ArticleListDto> getArticles(
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String favorited,
            @RequestParam(defaultValue = "0") @Min(0) int offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int limit
    ) {
        return ResponseEntity.ok(articleService.getArticles(tag, author, favorited, offset, limit));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody @Valid RegisterArticleDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.createArticle(dto));
    }

    @GetMapping("/feed")
    public ResponseEntity<ArticleListDto> articleFeed(
            @RequestParam(defaultValue = "0") @Min(0) int offset,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int limit
    ) {
        return ResponseEntity.ok(articleService.getRecentArticles(offset, limit));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponseDto> getArticle(
            @PathVariable("slug") 
            @Size(min = 3, max = 100)
            @Pattern(regexp = "^[a-z0-9-]+$") String slug
    ) {
        return ResponseEntity.ok(articleService.getArticle(slug));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteArticle(
            @PathVariable("slug")
            @Pattern(regexp = "^[a-z0-9-]+$") String slug
    ) {
        articleService.deleteArticle(slug);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{slug}")
    public ResponseEntity<ArticleResponseDto> updateArticle(
            @PathVariable("slug") 
            @Pattern(regexp = "^[a-z0-9-]+$") String slug,
            @RequestBody @Valid RegisterArticleDto dto
    ) {
        return ResponseEntity.ok(articleService.updateArticle(slug, dto));
    }
}
