package cn.graydove.security.token.setter.support;

import cn.graydove.security.properties.TokenProperties;
import cn.graydove.security.token.Token;
import cn.graydove.security.token.TokenSupportAssert;
import cn.graydove.security.token.setter.CookieTokenSetter;
import cn.graydove.security.utils.TokenUtils;

import java.util.Collections;

public class CookieBearerTokenSetter implements CookieTokenSetter, TokenSupportAssert {
    @Override
    public String getTokenValue(Token token) {
        assertSupport(token.getType());
        return TokenUtils.toBearerToken(token.getToken());
    }

    @Override
    public Iterable<String> getSupportTypes() {
        return Collections.singletonList(TokenProperties.TYPE_BEARER);
    }
}
