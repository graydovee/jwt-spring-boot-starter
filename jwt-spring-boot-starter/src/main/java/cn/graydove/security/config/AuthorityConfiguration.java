package cn.graydove.security.config;

import cn.graydove.security.token.manager.AuthorityAdapter;
import cn.graydove.security.token.manager.AuthorityConfigure;
import cn.graydove.security.token.manager.impl.AuthorityAdapterImpl;
import cn.graydove.security.token.manager.impl.DefaultAuthorityConfigure;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true", matchIfMissing = true)
public class AuthorityConfiguration {


    @Bean
    @ConditionalOnMissingBean(AuthorityConfigure.class)
    public AuthorityConfigure authorityConfigure() {
        return new DefaultAuthorityConfigure();
    }

    @Bean
    @ConditionalOnMissingBean(AuthorityAdapter.class)
    public AuthorityAdapter authorityAdapter(AuthorityConfigure authorityConfigure) {
        AuthorityAdapterImpl authorityAdapter = new AuthorityAdapterImpl();
        authorityConfigure.configure(authorityAdapter);
        return authorityAdapter;
    }

}
