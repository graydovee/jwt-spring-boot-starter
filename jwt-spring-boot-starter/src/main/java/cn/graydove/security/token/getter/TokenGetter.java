package cn.graydove.security.token.getter;

import cn.graydove.security.properties.TokenProperties;
import cn.graydove.security.token.TokenSupportAssert;

import javax.servlet.http.HttpServletRequest;

public interface TokenGetter extends TokenSupportAssert {

    default String getToken(HttpServletRequest request, TokenProperties properties) {
        assertSupport(properties.getType());
        return doGet(request, properties);
    }

    String doGet(HttpServletRequest request, TokenProperties properties);
}
