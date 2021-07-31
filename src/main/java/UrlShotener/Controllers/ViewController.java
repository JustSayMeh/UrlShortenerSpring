package UrlShotener.Controllers;

import UrlShotener.Entities.URLEntity;
import UrlShotener.Repositories.URLRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class ViewController {
    @Autowired
    private URLRepository urlRepository;
    @GetMapping("/")
    public String index(Model model)
    {
        model.addAttribute("module", "index");
        return "index";
    }
    @GetMapping("/links")
    public String links(Model model)
    {
        model.addAttribute("module", "links");
        return "links";
    }
    @GetMapping("/about")
    public String about(Model model)
    {
        model.addAttribute("module", "about");
        return "about";
    }
}
