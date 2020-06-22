package cn.graydove.security.token.manager;

import cn.graydove.security.token.authority.AuthorizeRequest;

public interface AuthorityMatcher {
    AuthorizeRequest match(String uri);
}
