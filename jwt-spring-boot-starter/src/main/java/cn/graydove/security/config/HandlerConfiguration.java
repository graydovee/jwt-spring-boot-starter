package cn.graydove.security.config;

import cn.graydove.security.handler.AuthenticationDenyHandler;
import cn.graydove.security.handler.AuthenticationSuccessHandler;
import cn.graydove.security.handler.DenyHandler;
import cn.graydove.security.handler.UnauthorizedHandler;
import cn.graydove.security.handler.support.DefaultAuthenticationDenyHandler;
import cn.graydove.security.handler.support.DefaultAuthenticationSuccessHandler;
import cn.graydove.security.handler.support.DefaultDenyHandler;
import cn.graydove.security.handler.support.DefaultUnauthorizedHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true", matchIfMissing = true)
public class HandlerConfiguration {


    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new DefaultAuthenticationSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationDenyHandler.class)
    public AuthenticationDenyHandler authenticationDenyHandler() {
        return new DefaultAuthenticationDenyHandler();
    }

    @Bean
    @ConditionalOnMissingBean(UnauthorizedHandler.class)
    public UnauthorizedHandler unauthorizedHandler() {
        return new DefaultUnauthorizedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(DenyHandler.class)
    public DenyHandler denyHandler(){
        return new DefaultDenyHandler();
    }
}
