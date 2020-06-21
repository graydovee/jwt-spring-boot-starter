package cn.graydove.security.token.authority.impl;

import cn.graydove.security.token.authority.AuthorityAssert;
import cn.graydove.security.userdetails.UserDetails;

public class Authenticated implements AuthorityAssert {
    @Override
    public boolean asserts(Iterable<String> authorities, UserDetails userDetails) {
        return userDetails != null;
    }
}
