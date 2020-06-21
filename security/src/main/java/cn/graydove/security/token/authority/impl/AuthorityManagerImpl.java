package cn.graydove.security.token.authority.impl;

import cn.graydove.security.token.authority.AuthorityManager;
import cn.graydove.security.token.authority.AuthorityMatcher;
import cn.graydove.security.token.authority.AuthorizeRequest;
import cn.graydove.security.token.authority.AuthorizeRequestBuilder;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;

public class AuthorityManagerImpl implements AuthorityManager, AuthorityMatcher {

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
    public AuthorizeRequestBuilder antMatchers(String pattern) {
        AuthorizeRequest authorizeRequest = new AuthorizeRequest();
        authorizeRequest.setPattern(pattern);
        return new AuthorizeRequestBuilder(authorizeRequest, this);
    }

    @Override
    public AuthorizeRequestBuilder anyRequest() {
        return new AuthorizeRequestBuilder(anyRequest, this);
    }

    @Override
    public AuthorizeRequest match(String uri) {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (AuthorizeRequest a: authorizeRequests) {
            if (antPathMatcher.match(a.getPattern(), uri)) {
                return a;
            }
        }
        return anyRequest;
    }


}
