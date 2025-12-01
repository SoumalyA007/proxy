package testBases;

import com.google.common.base.Utf8;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class urlEncode extends baseClass {

    public static String buildURL(String key){

        try{
            String keyword = URLEncoder.encode(key,StandardCharsets.UTF_8);
            return p.getProperty("appURL")+keyword;
        } catch (Exception e) {
            throw new RuntimeException("URL could not be made : "+e);
        }
    }
}
