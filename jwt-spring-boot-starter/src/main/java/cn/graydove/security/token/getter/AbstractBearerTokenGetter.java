package cn.graydove.security.token.getter;

import cn.graydove.security.properties.TokenProperties;
import cn.graydove.security.utils.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public abstract class AbstractBearerTokenGetter extends AbstractTokenGetter {
    @Override
    public final String doGet(HttpServletRequest request, TokenProperties properties) {
        String bearerToken = getBearerToken(request, properties);
        return TokenUtils.getTokenFromBearerToken(bearerToken);
    }

    public abstract String getBearerToken(HttpServletRequest request, TokenProperties properties);

    @Override
    public final Iterable<String> getSupportTypes() {
        return Collections.singletonList(TokenProperties.TYPE_BEARER);
    }
}
