package cn.graydove.security.token.manager;

import cn.graydove.security.token.authority.AuthorizeRequest;
import cn.graydove.security.token.authority.AuthorizeRequestBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

public interface AuthorityManager {

    void addAuthorizeRequest(AuthorizeRequest authorizeRequest);

    AuthorizeRequestBuilder antMatchers(RequestMethod requestMethod, String ... patterns);

    default AuthorizeRequestBuilder antMatchers(String ... patterns) {
        return antMatchers(null, patterns);
    }

    AuthorizeRequestBuilder anyRequest();
}
