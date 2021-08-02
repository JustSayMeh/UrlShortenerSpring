package UrlShotener.Handlers;

import UrlShotener.Entities.URLEntity;
import UrlShotener.Services.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@Scope("prototype")
public class ViewRedirectHandler {
    @Autowired
    private final URLService service;
    private String short_url;
    private boolean isInit = false;
    public ViewRedirectHandler(URLService service){
        this.service = service;
    }

    public void init(String short_url){
        this.short_url = short_url;
        isInit = true;
    }
    public ModelAndView handle(){
        if(service.containsShortUrl(short_url))
        {
            URLEntity entity = service.getByShortUrl(short_url);
            return new ModelAndView("redirect:" + entity.getOriginalUrl());
        }
        return new ModelAndView("redirect:/");
    }
}
