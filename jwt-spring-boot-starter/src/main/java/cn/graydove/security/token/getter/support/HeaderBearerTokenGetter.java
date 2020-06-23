package cn.graydove.security.token.getter.support;

import cn.graydove.security.properties.TokenProperties;
import cn.graydove.security.token.getter.BearerTokenGetter;
import cn.graydove.security.utils.TokenUtils;

import javax.servlet.http.HttpServletRequest;

public class HeaderBearerTokenGetter implements BearerTokenGetter {

    @Override
    public String doGet(HttpServletRequest request, TokenProperties properties) {
        String header = request.getHeader(properties.getTokenKey());
        if (header == null) {
            return null;
        }
        return TokenUtils.getTokenFromBearerToken(header);
    }
}
