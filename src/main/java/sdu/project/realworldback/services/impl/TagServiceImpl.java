package sdu.project.realworldback.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sdu.project.realworldback.dto.TagListDto;
import sdu.project.realworldback.repositories.TagRepository;
import sdu.project.realworldback.services.TagService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public TagListDto getTags() {

        List<String> tags = tagRepository.findAllDistinctTags();
        log.info("tags {}", tags);

        return TagListDto.builder()
                .tags(tags)
                .build();
    }
}
