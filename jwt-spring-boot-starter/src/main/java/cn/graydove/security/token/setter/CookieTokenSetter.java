package cn.graydove.security.token.setter;

import cn.graydove.security.token.Token;

public interface CookieTokenSetter {

    String getTokenValue(Token token);

}
