package UrlShotener.Handlers;

import UrlShotener.Entities.URLEntity;
import UrlShotener.Services.URLService;
import com.sun.jndi.toolkit.url.Uri;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class ViewLinksHandler {
    private final URLService service;
    private HttpServletRequest request;
    private Model model;
    private List<URLEntity> urls;
    public ViewLinksHandler(URLService service){
        this.service = service;
    }

    public void init(HttpServletRequest request, Model model){
        this.request = request;
        this.model = model;
        urls = new ArrayList<>();
    }
    public void handle(){
        Optional<Cookie> links_optional = getLinksFromCookie();
        if (links_optional.isPresent())
        {
            setUrlsFromCookie(links_optional);
        }
        setModel();
    }

    private Optional<Cookie> getLinksFromCookie(){
        return Arrays.stream(
                Optional.ofNullable(request.getCookies())
                        .orElseGet(() -> new Cookie[0])
        )
                .filter(str -> str.getName().equals("links"))
                .findFirst();
    }

    private void setUrlsFromCookie(Optional<Cookie> links_optional){
        String[] links = links_optional.get().getValue().split("\\&");
        urls = Arrays.stream(links)
                .filter(link -> service.containsShortUrl(link))
                .map(link -> service.getByShortUrl(link))
                .collect(Collectors.toList());
    }

    private void setModel(){
        model.addAttribute("module", "links");
        model.addAttribute("urls", urls);
        try {
            Uri uri = new Uri(request.getRequestURL().toString());
            model.addAttribute("host_domain", uri.getHost());
            model.addAttribute("host_scheme", uri.getScheme());
            model.addAttribute("host_port", uri.getPort());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
