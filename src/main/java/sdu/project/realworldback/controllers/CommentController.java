package sdu.project.realworldback.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sdu.project.realworldback.dto.CommentListDto;
import sdu.project.realworldback.dto.CommentResponseDto;
import sdu.project.realworldback.dto.RegisterCommentDto;
import sdu.project.realworldback.services.CommentService;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/articles/{slug}/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<CommentListDto> getComments(@PathVariable("slug") String slug){
        return ResponseEntity.ok(commentService.getComments(slug));
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable("slug") String slug,
                                                            @RequestBody @Valid RegisterCommentDto dto){
        return ResponseEntity.ok(commentService.createComment(slug, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("slug") String slug,
                                                    @PathVariable("id") Long id){
        commentService.deleteComment(slug, id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
}
