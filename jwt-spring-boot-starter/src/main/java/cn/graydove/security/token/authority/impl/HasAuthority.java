package cn.graydove.security.token.authority.impl;

import cn.graydove.security.token.authority.AuthorityAssert;
import cn.graydove.security.userdetails.GrantedAuthority;
import cn.graydove.security.userdetails.UserDetails;
import cn.graydove.security.userdetails.support.Authority;

import java.util.Objects;

public class HasAuthority implements AuthorityAssert {
    @Override
    public boolean asserts(Iterable<String> authorities, UserDetails userDetails) {
        if (userDetails == null || userDetails.getAuthorities() == null) {
            return false;
        }

        for (String a: authorities) {
            for (GrantedAuthority authority: userDetails.getAuthorities()){
                if (Objects.equals(authority.getAuthority(), a)) {
                    return true;
                }
            }
            break;
        }
        return false;
    }
}
