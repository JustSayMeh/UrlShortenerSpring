package UrlShotener.Controllers;

import UrlShotener.Entities.URLEntity;
import UrlShotener.Services.URLService;
import UrlShotener.Utils.Shortener;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

class Response {
    public String m1, m2;
    public Response(String url, String short_url){
        this.m1 = short_url;
        this.m2 = url;
    }
}
class Request {
    public String url, short_url;

}
class URlValidator{
    private Uri uri;
    private String url;
    public URlValidator(String url) throws MalformedURLException {
        this.url = url;
        setHttp();
        reduceWWW();
        uri = new Uri(this.url);
        String host = uri.getHost();
        this.url = this.url.replace(host, host.toLowerCase(Locale.ROOT));
    }
    private void setHttp(){
        if (!url.contains("http://") && !url.contains("https://"))
            url = "https://" + url;
    }
    private void reduceWWW() { url = url.replaceAll("://www\\.", "://"); }
    public String toString()
    {
        return url;
    }
}
@RestController
public class ApiController {
    @Autowired
    private URLService service;

    @PostMapping("/api/create")
    public Response create(@RequestBody Request req,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        String url = req.url;
        String short_url = req.short_url;
        String links = "";
        try {
            URlValidator uRlValidator = new URlValidator(url);
            url = uRlValidator.toString();
            if (short_url == null)
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
            else
            {

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new Response(req.url, "X" + short_url);
    }
}
