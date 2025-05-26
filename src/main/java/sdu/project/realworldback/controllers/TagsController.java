package sdu.project.realworldback.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sdu.project.realworldback.dto.TagListDto;
import sdu.project.realworldback.services.impl.TagServiceImpl;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tags")
public class TagsController {

    private final TagServiceImpl tagService;

    @GetMapping
    public ResponseEntity<TagListDto> getAllTags(){
        return ResponseEntity.ok(tagService.getTags());
    }
}
