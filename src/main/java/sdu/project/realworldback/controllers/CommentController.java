package sdu.project.realworldback.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import sdu.project.realworldback.dto.CommentListDto;
import sdu.project.realworldback.dto.CommentResponseDto;
import sdu.project.realworldback.dto.RegisterCommentDto;
import sdu.project.realworldback.services.CommentService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/articles/{slug}/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<CommentListDto> getComments(
            @PathVariable("slug") 
            @Pattern(regexp = "^[a-z0-9-]+$") String slug
    ){
        return ResponseEntity.ok(commentService.getComments(slug));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable("slug") 
            @Pattern(regexp = "^[a-z0-9-]+$") String slug,
            @RequestBody @Valid RegisterCommentDto dto
    ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(slug, dto));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("slug") 
            @Pattern(regexp = "^[a-z0-9-]+$") String slug,
            @PathVariable("id") 
            @Positive Long id
    ){
        commentService.deleteComment(slug, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
