package cn.graydove.security.config;

import cn.graydove.security.token.authority.AuthorityConfigure;
import cn.graydove.security.token.authority.impl.AuthorityManagerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true", matchIfMissing = true)
public class BeanConfiguration {

    @Bean
    @ConditionalOnMissingBean(AuthorityManagerImpl.class)
    public AuthorityManagerImpl authorityManager(AuthorityConfigure authorityConfigure) {
        AuthorityManagerImpl authorityManager = new AuthorityManagerImpl();
        authorityConfigure.configure(authorityManager);
        return authorityManager;
    }
}
