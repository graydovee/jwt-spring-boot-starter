package cn.graydove.security.token.authority;

import cn.graydove.security.token.authority.impl.Authenticated;
import cn.graydove.security.token.authority.impl.HasAuthority;
import cn.graydove.security.token.manager.impl.AuthorityAdapterImpl;
import cn.graydove.security.token.authority.impl.HasAnyAuthority;
import cn.graydove.security.token.authority.impl.PermitAll;

import java.util.Arrays;

public class AuthorizeRequestBuilder {
    private AuthorityAdapterImpl authorityManager;
    private AuthorizeRequest authorizeRequest;

    public AuthorizeRequestBuilder(AuthorizeRequest authorizeRequest, AuthorityAdapterImpl authorityManager) {
        this.authorityManager = authorityManager;
        this.authorizeRequest = authorizeRequest;
    }

    private AuthorityAdapterImpl build() {
        authorityManager.addAuthorizeRequest(authorizeRequest);
        return authorityManager;
    }

    public AuthorityAdapterImpl permitAll() {
        authorizeRequest.setAuthorityAssert(new PermitAll());
        return build();
    }

    public AuthorityAdapterImpl hasAnyAuthority(String ... authority) {
        authorizeRequest.setAuthorities(Arrays.asList(authority));
        authorizeRequest.setAuthorityAssert(new HasAnyAuthority());
        return build();
    }

    public AuthorityAdapterImpl hasAuthority(String ... authority) {
        authorizeRequest.setAuthorities(Arrays.asList(authority));
        authorizeRequest.setAuthorityAssert(new HasAuthority());
        return build();
    }

    public AuthorityAdapterImpl authenticated() {
        authorizeRequest.setAuthorityAssert(new Authenticated());
        return build();
    }

}
