package sdu.project.realworldback.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sdu.project.realworldback.models.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByTagListContaining(String tag, Pageable pageable);

    List<Article> findByAuthorUsername(String authorUsername, Pageable pageable);

    @Query(value = """
        SELECT a.* FROM article a
        JOIN user_followers u ON a.author_id = u.follower_id
        WHERE u.followed_id = :id
        ORDER BY a.updated_at DESC
        """, nativeQuery = true)
    List<Article> findAllArticlesByFollowers(@Param("id") Long id, Pageable pageable);

    Optional<Article> findBySlug(String slug);

    void deleteBySlug(String slug);
}
