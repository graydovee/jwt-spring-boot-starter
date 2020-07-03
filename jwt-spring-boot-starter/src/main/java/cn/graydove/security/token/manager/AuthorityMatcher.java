package cn.graydove.security.token.manager;

import cn.graydove.security.token.authority.AuthorizeRequest;
import org.springframework.web.bind.annotation.RequestMethod;

public interface AuthorityMatcher {
    AuthorizeRequest match(String uri, RequestMethod requestMethod);
}
