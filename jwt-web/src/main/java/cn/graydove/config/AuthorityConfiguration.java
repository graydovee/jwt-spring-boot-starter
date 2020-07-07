package cn.graydove.config;

import cn.graydove.security.token.manager.AuthorityConfigure;
import cn.graydove.security.token.manager.AuthorityManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

@Configuration
public class AuthorityConfiguration implements AuthorityConfigure {
    @Override
    public void configure(AuthorityManager authorityManager) {
        authorityManager
                .antMatchers("/register").permitAll()
                .antMatchers("/public/**").permitAll()
                .antMatchers(RequestMethod.POST,"/post").permitAll()
                .anyRequest().authenticated();
    }
}
