package cn.graydove.security.utils;

import cn.graydove.security.properties.TokenProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public final class TokenUtils {

    private TokenUtils(){}

    public static String getTokenFromBearerToken(String bearerToken) {
        if (bearerToken == null) {
            return null;
        }
        String prefix = TokenProperties.TYPE_BEARER;
        if (!(bearerToken.length() > prefix.length()) || !bearerToken.startsWith(prefix)) {
            return null;
        }
        return bearerToken.substring(prefix.length() + 1);
    }

    public static String toBearerToken(String token) {
        return TokenProperties.TYPE_BEARER + " " + token;
    }


    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        if (request == null || cookieName == null) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals(cookieName)) {
                return cookie;
            }
        }
        return null;
    }
}
