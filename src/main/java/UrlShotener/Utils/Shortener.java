package UrlShotener.Utils;

import com.google.bitcoin.core.Base58;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.Charset;
import java.util.function.Predicate;

public class Shortener {
    public static String do_short(String original_string, Predicate<String> predicate)
    {
        String sha256 = DigestUtils.sha256Hex(original_string);
        String short_string = Base58.encode(sha256.getBytes(Charset.defaultCharset()))
                .substring(0, 7);
        while(predicate.test(short_string))
        {
            short_string = DigestUtils.sha256Hex(short_string);
            short_string = Base58.encode(short_string.getBytes(Charset.defaultCharset()))
                    .substring(0, 7);
        }
        return short_string;
    }
}
