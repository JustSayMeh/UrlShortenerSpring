package UrlShortener.Utils;

import com.sun.jndi.toolkit.url.Uri;

import java.net.MalformedURLException;
import java.util.Locale;

public class URLValidator {
    private Uri uri;
    private String url;
    public URLValidator(String url) throws MalformedURLException {
        this.url = url;
        setHttp();
        reduceWWW();
        uri = new Uri(this.url);
        String host = uri.getHost();
        this.url = this.url.replace(host, host.toLowerCase(Locale.ROOT));
    }
    private void setHttp(){
        if (!url.matches("^\\w+(://).*"))
            url = "https://" + url;
    }
    private void reduceWWW() { url = url.replaceAll("://www\\.", "://"); }
    public String toString()
    {
        return url;
    }
}
