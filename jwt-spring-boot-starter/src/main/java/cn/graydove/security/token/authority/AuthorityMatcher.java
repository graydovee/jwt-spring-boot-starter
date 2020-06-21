package cn.graydove.security.token.authority;

public interface AuthorityMatcher {
    AuthorizeRequest match(String uri);
}
