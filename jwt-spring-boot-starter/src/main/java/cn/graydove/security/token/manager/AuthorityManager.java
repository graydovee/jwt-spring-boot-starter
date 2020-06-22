package cn.graydove.security.token.manager;

import cn.graydove.security.token.authority.AuthorizeRequest;
import cn.graydove.security.token.authority.AuthorizeRequestBuilder;

public interface AuthorityManager {

    void addAuthorizeRequest(AuthorizeRequest authorizeRequest);

    AuthorizeRequestBuilder antMatchers(String pattern);

    AuthorizeRequestBuilder anyRequest();
}
