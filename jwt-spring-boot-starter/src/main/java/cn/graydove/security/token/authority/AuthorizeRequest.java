package cn.graydove.security.token.authority;

import cn.graydove.security.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AuthorizeRequest {
    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    private List<String> patterns = new LinkedList<>();
    private RequestMethod requestMethod = null;
    private List<String> authorities = new ArrayList<>();

    private AuthorityAssert authorityAssert;

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public void setAuthorityAssert(AuthorityAssert authorityAssert) {
        this.authorityAssert = authorityAssert;
    }

    public AuthorityAssert getAuthorityAssert() {
        return authorityAssert;
    }

    public void addPattern(String pattern) {
        this.patterns.add(pattern);
    }

    public void addPattern(String[] patterns) {
        for (String pattern : patterns) {
            addPattern(pattern);
        }
    }

    public boolean asserts(UserDetails userDetails) {
        if (authorityAssert == null) {
            return false;
        }
        return authorityAssert.asserts(authorities, userDetails);
    }

    public boolean matchPattern(String uri) {
        for (String pattern : patterns) {
            if (antPathMatcher.match(pattern, uri)) {
                return true;
            }
        }
        return false;
    }

    public boolean matchMethod(RequestMethod requestMethod) {
        return this.requestMethod == null || this.requestMethod == requestMethod;
    }

    public boolean match(RequestMethod requestMethod, String uri) {
        return matchMethod(requestMethod) && matchPattern(uri);
    }
}
