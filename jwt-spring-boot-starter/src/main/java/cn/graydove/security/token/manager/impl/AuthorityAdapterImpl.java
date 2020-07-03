package cn.graydove.security.token.manager.impl;

import cn.graydove.security.token.manager.AuthorityAdapter;
import cn.graydove.security.token.authority.AuthorizeRequest;
import cn.graydove.security.token.authority.AuthorizeRequestBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

public class AuthorityAdapterImpl implements AuthorityAdapter {

    private List<AuthorizeRequest> authorizeRequests = new ArrayList<>();

    private AuthorizeRequest anyRequest = new AuthorizeRequest();

    @Override
    public void addAuthorizeRequest(AuthorizeRequest authorizeRequest) {
        if (anyRequest.equals(authorizeRequest)) {
            return;
        }
        authorizeRequests.add(authorizeRequest);
    }

    @Override
    public AuthorizeRequestBuilder antMatchers(RequestMethod requestMethod, String ... patterns) {
        AuthorizeRequest authorizeRequest = new AuthorizeRequest();
        authorizeRequest.addPattern(patterns);
        authorizeRequest.setRequestMethod(requestMethod);
        return new AuthorizeRequestBuilder(authorizeRequest, this);
    }

    @Override
    public AuthorizeRequestBuilder anyRequest() {
        return new AuthorizeRequestBuilder(anyRequest, this);
    }

    @Override
    public AuthorizeRequest match(String uri, RequestMethod requestMethod) {
        for (AuthorizeRequest authorizeRequest: authorizeRequests) {
            if (authorizeRequest.match(requestMethod, uri)) {
                return authorizeRequest;
            }
        }
        return anyRequest;
    }


}
