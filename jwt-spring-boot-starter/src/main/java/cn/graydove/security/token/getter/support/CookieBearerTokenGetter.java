package cn.graydove.security.token.getter.support;

import cn.graydove.security.properties.TokenProperties;
import cn.graydove.security.token.getter.AbstractBearerTokenGetter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CookieBearerTokenGetter extends AbstractBearerTokenGetter {

    @Override
    public String getBearerToken(HttpServletRequest request, TokenProperties properties) {
        for (Cookie cookie: request.getCookies()) {
            if (cookie.getName().equals(properties.getTokenKey())) {
                try {
                    return URLDecoder.decode(cookie.getValue(), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            }
        }
        return null;
    }

}
