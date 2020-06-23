package cn.graydove.security.token.getter.support;

import cn.graydove.security.token.getter.AbstractCookieTokenGetter;
import cn.graydove.security.token.getter.BearerTokenGetter;
import cn.graydove.security.utils.TokenUtils;

import javax.servlet.http.Cookie;

public class CookieBearerTokenGetter extends AbstractCookieTokenGetter implements BearerTokenGetter {

    @Override
    public String getTokenFromCookie(Cookie cookie) {
        String value = getValue(cookie);
        return TokenUtils.getTokenFromBearerToken(value);
    }

}
