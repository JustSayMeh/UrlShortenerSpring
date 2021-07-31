package UrlShotener.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(indexes = @Index(columnList = "shortUrl"))
public class URLEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String shortUrl;
    @Column(unique = true)
    private String originalUrl;
    private Date createDate;
    private Date lastRedirectDate;



    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public static URLEntity generateFromURL(String original_url)
    {
        URLEntity urlEntity = new URLEntity();
        urlEntity.setOriginalUrl(original_url);
        urlEntity.setOriginalUrl("short");
        urlEntity.setCreateDate(new Date());
        //urlEntity.setId(0l);
        return urlEntity;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastRedirectDate() {
        return lastRedirectDate;
    }

    public void setLastRedirectDate(Date lastRedirectDate) {
        this.lastRedirectDate = lastRedirectDate;
    }
}
