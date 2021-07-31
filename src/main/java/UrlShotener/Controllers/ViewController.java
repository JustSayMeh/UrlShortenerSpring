package UrlShotener.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ViewController {
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
