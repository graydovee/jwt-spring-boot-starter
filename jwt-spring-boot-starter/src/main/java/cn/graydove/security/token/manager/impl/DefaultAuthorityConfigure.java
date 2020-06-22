package cn.graydove.security.token.manager.impl;

import cn.graydove.security.token.manager.AuthorityConfigure;
import cn.graydove.security.token.manager.AuthorityManager;

public class DefaultAuthorityConfigure implements AuthorityConfigure {

    @Override
    public void configure(AuthorityManager authorityManager) {
        authorityManager.anyRequest().authenticated();
    }

}
