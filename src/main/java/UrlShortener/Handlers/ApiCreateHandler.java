package UrlShortener.Handlers;

import UrlShortener.Entities.URLEntity;
import UrlShortener.Services.URLService;
import UrlShortener.Utils.ResponseJson;
import UrlShortener.Utils.Shortener;
import UrlShortener.Utils.URLValidator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Component
@Scope("prototype")
public class ApiCreateHandler {
    private final URLService service;
    private boolean is_init = false;
    private String url, shortUrl;
    public ApiCreateHandler(URLService service){
        this.service = service;
    }
    public void init(String url, String short_url)
    {
        this.url = url;
        this.shortUrl = short_url;
        is_init = true;
    }

    public ResponseJson handle() throws MalformedURLException {
            validateOriginUrl();
            if (shortUrlIsEmpty())
            {
                setShortUrlOrLoadFromDb();
            }
            else
            {
                tryToSetShortUrlFromUser();
            }
        return new ResponseJson("X" + shortUrl, url);
    }

    private void validateOriginUrl() throws MalformedURLException {
        URLValidator uRlValidator = new URLValidator(url);
        url = uRlValidator.toString();
    }

    private boolean shortUrlIsEmpty() {
        return shortUrl == null;
    }
    private void setShortUrlOrLoadFromDb()
    {
        if (!service.containsOriginalUrl(url))
        {
            shortUrl = Shortener.doShort(url, (str) -> service.containsShortUrl(str));
            service.insert(URLEntity.generateFromURL(url, shortUrl));
        }
        else
        {
            shortUrl = service.getByOriginalUrl(url).getShortUrl();
        }
    }
    private void tryToSetShortUrlFromUser()
    {
        // TODO
    }
}
