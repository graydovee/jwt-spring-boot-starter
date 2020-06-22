package cn.graydove.security.token.getter.support;

import cn.graydove.security.properties.TokenProperties;
import cn.graydove.security.token.getter.AbstractBearerTokenGetter;

import javax.servlet.http.HttpServletRequest;

public class HeaderBearerTokenGetter extends AbstractBearerTokenGetter {

    @Override
    public String getBearerToken(HttpServletRequest request, TokenProperties properties) {
        return request.getHeader(properties.getTokenKey());
    }
}
