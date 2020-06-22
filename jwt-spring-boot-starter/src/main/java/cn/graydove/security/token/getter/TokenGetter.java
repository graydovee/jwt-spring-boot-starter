package cn.graydove.security.token.getter;

import cn.graydove.security.properties.TokenProperties;

import javax.servlet.http.HttpServletRequest;

public interface TokenGetter {

    String getToken(HttpServletRequest request, TokenProperties properties);
}
