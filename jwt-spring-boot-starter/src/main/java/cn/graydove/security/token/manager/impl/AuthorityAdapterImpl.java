package cn.graydove.security.token.manager.impl;

import cn.graydove.security.token.HttpMethodType;
import cn.graydove.security.token.manager.AuthorityAdapter;
import cn.graydove.security.token.authority.AuthorizeRequest;
import cn.graydove.security.token.authority.AuthorizeRequestBuilder;
import org.springframework.util.AntPathMatcher;

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
    public AuthorizeRequestBuilder antMatchers(HttpMethodType httpMethodType, String ... patterns) {
        AuthorizeRequest authorizeRequest = new AuthorizeRequest();
        authorizeRequest.addPattern(patterns);
        authorizeRequest.setHttpMethodType(httpMethodType);
        return new AuthorizeRequestBuilder(authorizeRequest, this);
    }

    @Override
    public AuthorizeRequestBuilder anyRequest() {
        return new AuthorizeRequestBuilder(anyRequest, this);
    }

    @Override
    public AuthorizeRequest match(String uri, HttpMethodType httpMethodType) {
        for (AuthorizeRequest authorizeRequest: authorizeRequests) {
            if (authorizeRequest.match(httpMethodType, uri)) {
                return authorizeRequest;
            }
        }
        return anyRequest;
    }


}
