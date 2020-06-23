package cn.graydove.security.token.getter;

import cn.graydove.security.properties.TokenProperties;
import cn.graydove.security.utils.TokenUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public abstract class AbstractCookieTokenGetter implements TokenGetter {


    @Override
    public final String doGet(HttpServletRequest request, TokenProperties properties) {
        Cookie cookie = TokenUtils.getCookie(request, properties.getTokenKey());
        if (cookie == null) {
            return null;
        }

        return getTokenFromCookie(cookie);
    }

    public abstract String getTokenFromCookie(Cookie cookie);

    public final String getValue(Cookie cookie) {
        try {
            return URLDecoder.decode(cookie.getValue(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
