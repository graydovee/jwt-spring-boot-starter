package cn.graydove.config;

import cn.graydove.security.token.manager.AuthorityConfigure;
import cn.graydove.security.token.manager.AuthorityManager;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthorityConfiguration implements AuthorityConfigure {
    @Override
    public void configure(AuthorityManager authorityManager) {
        authorityManager.antMatchers("/register").permitAll().anyRequest().authenticated();
    }
}
