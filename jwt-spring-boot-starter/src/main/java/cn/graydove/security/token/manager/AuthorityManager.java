package cn.graydove.security.token.manager;

import cn.graydove.security.token.HttpMethodType;
import cn.graydove.security.token.authority.AuthorizeRequest;
import cn.graydove.security.token.authority.AuthorizeRequestBuilder;

public interface AuthorityManager {

    void addAuthorizeRequest(AuthorizeRequest authorizeRequest);

    AuthorizeRequestBuilder antMatchers(HttpMethodType httpMethodType, String ... patterns);

    default AuthorizeRequestBuilder antMatchers(String ... patterns) {
        return antMatchers(HttpMethodType.ALL, patterns);
    }

    AuthorizeRequestBuilder anyRequest();
}
