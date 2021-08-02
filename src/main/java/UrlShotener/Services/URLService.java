package UrlShotener.Services;

import UrlShotener.Entities.URLEntity;
import UrlShotener.Repositories.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URLService {
    @Autowired
    private URLRepository urlRepository;
    public boolean containsShortUrl(String short_url)
    {
        return urlRepository.findByShortUrl(short_url).size() != 0;
    }

    public URLEntity getByShortUrl(String short_url)
    {
        return urlRepository.findByShortUrl(short_url)
                .stream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public boolean containsOriginalUrl(String original_url)
    {
        return urlRepository.findByOriginalUrl(original_url).size() != 0;
    }
    public URLEntity getByOriginalUrl(String original_url)
    {
        return urlRepository.findByOriginalUrl(original_url)
                .stream()
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
    public void insert(URLEntity urlEntity){
        urlRepository.save(urlEntity);
    }
}
