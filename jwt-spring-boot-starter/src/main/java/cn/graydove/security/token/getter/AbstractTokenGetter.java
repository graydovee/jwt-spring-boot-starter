package cn.graydove.security.token.getter;

import cn.graydove.security.exception.NotSupportException;
import cn.graydove.security.properties.TokenProperties;

import javax.servlet.http.HttpServletRequest;

public abstract class AbstractTokenGetter implements TokenGetter {
    @Override
    public final String getToken(HttpServletRequest request, TokenProperties properties) {
        assertSupport(properties.getType());
        return doGet(request, properties);
    }

    public abstract String doGet(HttpServletRequest request, TokenProperties properties);

    public abstract Iterable<String> getSupportTypes();

    public void assertSupport(String type) {
        for (String supportType: getSupportTypes()) {
            if (supportType.equals(type)) {
                return;
            }
        }
        throw new NotSupportException("支持该类型的Token，请使用合适的TokenGetter");
    }
}
