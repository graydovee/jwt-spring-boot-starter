package cn.graydove.security.token.authority.impl;

import cn.graydove.security.token.authority.AuthorityAssert;
import cn.graydove.security.userdetails.GrantedAuthority;
import cn.graydove.security.userdetails.UserDetails;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class HasAnyAuthority implements AuthorityAssert {

    @Override
    public boolean asserts(Iterable<String> authorities, UserDetails userDetails) {
        if (userDetails == null || userDetails.getAuthorities() == null) {
            return false;
        }
        List<? extends GrantedAuthority> au = userDetails.getAuthorities();
        for (String a: authorities) {
            for (GrantedAuthority authority: au) {
                if (StringUtils.equalsIgnoreCase(authority.getAuthority(), a)) {
                    return true;
                }
            }
        }
        return false;
    }
}
