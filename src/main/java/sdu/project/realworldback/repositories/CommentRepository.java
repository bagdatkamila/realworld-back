package sdu.project.realworldback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sdu.project.realworldback.models.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByArticleSlug(String slug);
}
