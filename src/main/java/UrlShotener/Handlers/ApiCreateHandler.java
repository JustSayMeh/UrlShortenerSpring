package UrlShotener.Handlers;

import UrlShotener.Entities.URLEntity;
import UrlShotener.Services.URLService;
import UrlShotener.Utils.ResponseJson;
import UrlShotener.Utils.Shortener;
import UrlShotener.Utils.URLValidator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

@Component
@Scope("prototype")
public class ApiCreateHandler {
    private final URLService service;
    private boolean is_init = false;
    private String url, short_url;
    public ApiCreateHandler(URLService service){
        this.service = service;
    }
    public void init(String url, String short_url)
    {
        this.url = url;
        this.short_url = short_url;
        is_init = true;
    }

    public ResponseJson handle() throws MalformedURLException {
            validate_origin_url();
            if (short_url_is_empty())
            {
                set_short_url_or_load_from_db();
            }
            else
            {
                try_to_set_short_url_from_user();
            }
        return new ResponseJson("X" + short_url, url);
    }

    private void validate_origin_url() throws MalformedURLException {
        URLValidator uRlValidator = new URLValidator(url);
        url = uRlValidator.toString();
    }

    private boolean short_url_is_empty() {
        return short_url == null;
    }
    private void set_short_url_or_load_from_db()
    {
        if (!service.contains_original_url(url))
        {
            short_url = Shortener.do_short(url, (str) -> service.contains_short_url(str));
            service.insert(URLEntity.generateFromURL(url, short_url));
        }
        else
        {
            short_url = service.get_by_original_url(url).getShortUrl();
        }
    }
    private void try_to_set_short_url_from_user()
    {
        // TODO
    }
}
