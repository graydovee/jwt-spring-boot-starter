package cn.graydove.security.config;

import cn.graydove.security.crypto.PasswordEncoder;
import cn.graydove.security.crypto.support.BCryptPasswordEncoder;
import cn.graydove.security.properties.DefaultProperties;
import cn.graydove.security.token.JwtTokenManager;
import cn.graydove.security.token.TokenManager;
import cn.graydove.security.properties.JwtProperties;
import cn.graydove.security.token.getter.TokenGetter;
import cn.graydove.security.token.getter.support.HeaderBearerTokenGetter;
import cn.graydove.security.token.setter.CookieTokenSetter;
import cn.graydove.security.token.setter.support.CookieBearerTokenSetter;
import cn.graydove.security.userdetails.UserDetailService;
import cn.graydove.security.userdetails.support.Authority;
import cn.graydove.security.userdetails.support.DefaultUserDetailService;
import cn.graydove.security.userdetails.support.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true", matchIfMissing = true)
public class JwtConfiguration {

    private JwtProperties jwtProperties;

    public JwtConfiguration(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Bean
    @ConditionalOnMissingBean(TokenManager.class)
    public TokenManager tokenManager(){
        return new JwtTokenManager(jwtProperties.getToken());
    }


    @Bean
    @ConditionalOnMissingBean(UserDetailService.class)
    public UserDetailService userDetailService(PasswordEncoder passwordEncoder) {
        DefaultProperties defaults = jwtProperties.getDefaults();
        User user = new User();
        user.setUsername(defaults.getUsername());
        user.setPassword(passwordEncoder.encode(defaults.getPassword()));
        user.setAuthorities(Authority.getAuthoritiesByString(defaults.getAuthorities()));
        return new DefaultUserDetailService(user);
    }

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @ConditionalOnMissingBean(TokenGetter.class)
    public TokenGetter tokenGetter() {
        return new HeaderBearerTokenGetter();
    }

    @Bean
    @ConditionalOnMissingBean(CookieTokenSetter.class)
    public CookieTokenSetter cookieTokenSetter() {
        return new CookieBearerTokenSetter();
    }

}
