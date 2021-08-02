package UrlShotener.Controllers;

import UrlShotener.Handlers.ViewLinksHandler;
import UrlShotener.Handlers.ViewRedirectHandler;
import UrlShotener.Services.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ViewController {
    @Autowired
    ViewRedirectHandler redirectHandler;
    @Autowired
    ViewLinksHandler linksHandler;
    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("module", "index");
        return "index";
    }
    @GetMapping("/links")
    public String links(Model model, HttpServletRequest request)
    {
        linksHandler.init(request, model);
        linksHandler.handle();
        return "links";
    }
    @GetMapping("/about")
    public String about(Model model)
    {
        model.addAttribute("module", "about");
        return "about";
    }

    @GetMapping("/X{short_url}")
    public ModelAndView redirectShortUrl(
            @PathVariable(value="short_url")  String short_url
            )
    {
        redirectHandler.init(short_url);
        return redirectHandler.handle();
    }
}
