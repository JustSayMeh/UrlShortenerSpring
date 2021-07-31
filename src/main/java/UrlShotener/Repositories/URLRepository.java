package UrlShotener.Repositories;

import UrlShotener.Entities.URLEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface URLRepository extends JpaRepository<URLEntity, Long> {
    @Modifying
    @Query("update URLEntity set lastRedirectDate = ?2 where shortUrl = ?1")
    int setLastRedirectDate(String short_url, Date redirect_date);
    List<URLEntity> findByShortUrl(String short_url);
    List<URLEntity> findByOriginalUrl(String original_url);


}
