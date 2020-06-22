package cn.graydove.config;

import cn.graydove.security.token.getter.TokenGetter;
import cn.graydove.security.token.getter.support.CookieBearerTokenGetter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Bean
    public TokenGetter tokenGetter() {
        return new CookieBearerTokenGetter();
    }
}
