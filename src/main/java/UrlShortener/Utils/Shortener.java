package UrlShortener.Utils;

import com.google.bitcoin.core.Base58;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.Charset;
import java.util.function.Predicate;

public class Shortener {
    public static String doShort(String originalString, Predicate<String> predicate)
    {
        String sha256 = DigestUtils.sha256Hex(originalString);
        String shortString = Base58.encode(sha256.getBytes(Charset.defaultCharset()))
                .substring(0, 7);
        while(predicate.test(shortString))
        {
            shortString = DigestUtils.sha256Hex(shortString);
            shortString = Base58.encode(shortString.getBytes(Charset.defaultCharset()))
                    .substring(0, 7);
        }
        return shortString;
    }
}
