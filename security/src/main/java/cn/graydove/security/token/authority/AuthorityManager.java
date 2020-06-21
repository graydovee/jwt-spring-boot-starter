package cn.graydove.security.token.authority;

public interface AuthorityManager {

    void addAuthorizeRequest(AuthorizeRequest authorizeRequest);

    AuthorizeRequestBuilder antMatchers(String pattern);

    AuthorizeRequestBuilder anyRequest();
}
