package UrlShotener.Controllers;

import UrlShotener.Entities.URLEntity;
import UrlShotener.Repositories.URLRepository;
import UrlShotener.Services.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ViewController {
    @Autowired
    private URLService service;
    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("module", "index");
        return "index";
    }
    @GetMapping("/links")
    public String links(Model model, HttpServletRequest request)
    {
        List<URLEntity> urls = new ArrayList<>();
        Optional<Cookie> links_optional = Arrays.stream(request.getCookies())
                .filter(str -> str.getName().equals("links"))
                .findFirst();
        if (links_optional.isPresent())
        {
            String[] links = links_optional.get().getValue().split("\\&");
            urls = Arrays.stream(links)
                    .filter(link -> link.length() > 0)
                    .map(link -> service.get_by_short_url(link)).collect(Collectors.toList());
        }
        model.addAttribute("module", "links");
        model.addAttribute("urls", urls);
        return "links";
    }
    @GetMapping("/about")
    public String about(Model model)
    {
        model.addAttribute("module", "about");
        return "about";
    }

    @GetMapping("/X{short_url}")
    public ModelAndView redirect_short_url(
            @PathVariable(value="short_url")  String short_url,
            HttpServletResponse httpServletResponse)
    {
        if(service.contains_short_url(short_url))
        {
            URLEntity entity = service.get_by_short_url(short_url);
            return new ModelAndView("redirect:" + entity.getOriginalUrl());
        }
        return new ModelAndView("redirect:/");
    }
}
