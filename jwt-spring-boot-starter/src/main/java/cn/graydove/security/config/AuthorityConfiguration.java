package cn.graydove.security.config;

import cn.graydove.security.token.authority.AuthorityConfigure;
import cn.graydove.security.token.authority.AuthorityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true", matchIfMissing = true)
@ConditionalOnMissingBean(AuthorityConfigure.class)
public class AuthorityConfiguration implements AuthorityConfigure {

    @Override
    public void configure(AuthorityManager authorityManager) {
        authorityManager.anyRequest().authenticated();
    }
}
