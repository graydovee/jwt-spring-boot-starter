package cn.graydove.security.token.authority;

import cn.graydove.security.token.HttpMethodType;
import cn.graydove.security.userdetails.GrantedAuthority;
import cn.graydove.security.userdetails.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AuthorizeRequest {
    private static AntPathMatcher antPathMatcher = new AntPathMatcher();

    private List<String> patterns = new LinkedList<>();
    private HttpMethodType httpMethodType = HttpMethodType.ALL;
    private List<String> authorities = new ArrayList<>();

    private AuthorityAssert authorityAssert;

    public void setHttpMethodType(HttpMethodType httpMethodType) {
        if (httpMethodType != null) {
            this.httpMethodType = httpMethodType;
        }
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

    public boolean matchMethod(HttpMethodType httpMethodType) {
        return this.httpMethodType == HttpMethodType.ALL || this.httpMethodType == httpMethodType;
    }

    public boolean matchMethod(String httpMethodTypeName) {
        return matchMethod(HttpMethodType.valueOf(httpMethodTypeName));
    }

    public boolean match(HttpMethodType httpMethodType, String uri) {
        return matchMethod(httpMethodType) && matchPattern(uri);
    }
}
