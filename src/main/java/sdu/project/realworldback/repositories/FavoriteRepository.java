package sdu.project.realworldback.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sdu.project.realworldback.models.Article;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Article, Long> {

    @Query("select a from Article a Join a.favoritedUsers u where  u.username = :username")
    List<Article> findAllByFavoritedByUsername(@Param("username") String username, Pageable pageable);

}
