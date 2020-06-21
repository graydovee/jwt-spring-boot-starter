package cn.graydove.security.token.authority;

import cn.graydove.security.token.authority.impl.Authenticated;
import cn.graydove.security.token.authority.impl.AuthorityManagerImpl;
import cn.graydove.security.token.authority.impl.HasAnyAuthority;
import cn.graydove.security.token.authority.impl.PermitAll;

import java.util.Arrays;
import java.util.Collections;

public class AuthorizeRequestBuilder {
    private AuthorityManagerImpl authorityManager;
    private AuthorizeRequest authorizeRequest;

    public AuthorizeRequestBuilder(AuthorizeRequest authorizeRequest, AuthorityManagerImpl authorityManager) {
        this.authorityManager = authorityManager;
        this.authorizeRequest = authorizeRequest;
    }

    private AuthorityManagerImpl build() {
        authorityManager.addAuthorizeRequest(authorizeRequest);
        return authorityManager;
    }

    public AuthorityManagerImpl permitAll() {
        authorizeRequest.setAuthorityAssert(new PermitAll());
        return build();
    }

    public AuthorityManagerImpl hasAnyAuthority(String ... authority) {
        authorizeRequest.setAuthorities(Arrays.asList(authority));
        authorizeRequest.setAuthorityAssert(new HasAnyAuthority());
        return build();
    }

    public AuthorityManagerImpl hasAuthority(String authority) {
        authorizeRequest.setAuthorities(Collections.singletonList(authority));
        authorizeRequest.setAuthorityAssert(new HasAnyAuthority());
        return build();
    }

    public AuthorityManagerImpl authenticated() {
        authorizeRequest.setAuthorityAssert(new Authenticated());
        return build();
    }

}
