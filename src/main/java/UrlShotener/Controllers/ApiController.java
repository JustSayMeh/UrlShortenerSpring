package UrlShotener.Controllers;

import UrlShotener.Entities.URLEntity;
import UrlShotener.RequestHandlers.ApiCreateHandler;
import UrlShotener.Services.URLService;
import UrlShotener.Utils.RequestJson;
import UrlShotener.Utils.ResponseJson;
import UrlShotener.Utils.Shortener;
import UrlShotener.Utils.URLValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.MalformedURLException;




@RestController
public class ApiController {
    @Autowired
    ApiCreateHandler handler;
    @PostMapping("/api/create")
    public ResponseJson create(@RequestBody RequestJson req, HttpServletResponse response) {
        handler.init(req.url, req.short_url);
        try {
            return handler.handle();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            response.setStatus(400);
            return new ResponseJson("Invalid url", "");
        }
    }
}
