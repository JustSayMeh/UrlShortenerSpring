package UrlShotener.Handlers;

import UrlShotener.Entities.URLEntity;
import UrlShotener.Services.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@Scope("Prototype")
public class ViewRedirectHandler {
    @Autowired
    private final URLService service;
    private String short_url;
    private boolean is_init = false;
    public ViewRedirectHandler(URLService service){
        this.service = service;
    }

    public void init(String short_url){
        this.short_url = short_url;
        is_init = true;
    }
    public ModelAndView handle(){
        if(service.contains_short_url(short_url))
        {
            URLEntity entity = service.get_by_short_url(short_url);
            return new ModelAndView("redirect:" + entity.getOriginalUrl());
        }
        return new ModelAndView("redirect:/");
    }
}
