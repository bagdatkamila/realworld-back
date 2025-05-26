package sdu.project.realworldback.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sdu.project.realworldback.dto.CommentDto;
import sdu.project.realworldback.dto.CommentListDto;
import sdu.project.realworldback.dto.CommentResponseDto;
import sdu.project.realworldback.dto.RegisterCommentDto;
import sdu.project.realworldback.dto.mappers.CommentMapper;
import sdu.project.realworldback.exceptions.AccessDeniedException;
import sdu.project.realworldback.exceptions.ResourceNotFoundException;
import sdu.project.realworldback.models.Article;
import sdu.project.realworldback.models.Comment;
import sdu.project.realworldback.models.Person;
import sdu.project.realworldback.repositories.ArticleRepository;
import sdu.project.realworldback.repositories.CommentRepository;
import sdu.project.realworldback.services.CommentService;
import sdu.project.realworldback.services.props.PersonDetailsService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final PersonDetailsService personDetailsService;
    private final CommentMapper commentMapper;

    @Override
    public CommentListDto getComments(String slug) {

        Person currentPerson = personDetailsService.getCurrentUser().orElse(null);
        List<Comment> comments = commentRepository.findAllByArticleSlug(slug);
        List<CommentDto> commentDtos = commentMapper.toDtoList(comments, currentPerson);

        return CommentListDto.builder()
                .comments(commentDtos)
                .build();
    }

    @Override
    @Transactional
    public CommentResponseDto createComment(String slug, RegisterCommentDto dto) {
        Person currentPerson = personDetailsService.getCurrentUser()
                .orElseThrow(() -> new AccessDeniedException("Unauthenticated"));
        Article article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article with slug: " + slug + " not found."));

        Comment comment = new Comment();
        comment.setBody(dto.getComment().getBody());
        comment.setArticle(article);
        comment.setAuthor(currentPerson);

        commentRepository.save(comment);

        CommentDto commentDto = commentMapper.toDto(comment, currentPerson);

        return CommentResponseDto.builder()
                .comment(commentDto)
                .build();
    }

    @Override
    @Transactional
    public void deleteComment(String slug, Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found."));

        commentRepository.delete(comment);
    }
}
