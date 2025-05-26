package sdu.project.realworldback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sdu.project.realworldback.models.Article;

import java.util.List;

public interface TagRepository extends JpaRepository<Article, Long> {

    @Query(value = "select distinct tag from article_tags", nativeQuery = true)
    List<String> findAllDistinctTags();

}
