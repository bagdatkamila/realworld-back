package sdu.project.realworldback.services;

import sdu.project.realworldback.dto.CommentListDto;
import sdu.project.realworldback.dto.CommentResponseDto;
import sdu.project.realworldback.dto.RegisterCommentDto;

public interface CommentService {


    CommentListDto getComments(String slug);

    CommentResponseDto createComment(String slug, RegisterCommentDto dto);

    void deleteComment(String slug, Long id);
}
