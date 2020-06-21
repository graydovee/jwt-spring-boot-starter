package cn.graydove.security.token.authority;

import cn.graydove.security.userdetails.UserDetails;

public interface AuthorityAssert {
    boolean asserts(Iterable<String> authorities, UserDetails userDetails);

}
