package cn.graydove.security.config;

import cn.graydove.security.crypto.PasswordEncoder;
import cn.graydove.security.filter.LoginFilter;
import cn.graydove.security.filter.TokenFilter;
import cn.graydove.security.handler.AuthenticationDenyHandler;
import cn.graydove.security.handler.AuthenticationSuccessHandler;
import cn.graydove.security.handler.DenyHandler;
import cn.graydove.security.handler.UnauthorizedHandler;
import cn.graydove.security.token.TokenManager;
import cn.graydove.security.properties.JwtProperties;
import cn.graydove.security.token.authority.AuthorityMatcher;
import cn.graydove.security.userdetails.UserDetailService;
import cn.graydove.security.userdetails.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.ConditionalOnMissingFilterBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
@ConditionalOnProperty(prefix = "jwt", name = "enable", havingValue = "true", matchIfMissing = true)
public class FilterConfiguration {

    private ObjectMapper objectMapper;

    private JwtProperties jwtProperties;

    private PasswordEncoder passwordEncoder;

    private TokenManager tokenManager;

    private UserDetailService userDetailService;

    public FilterConfiguration(ObjectMapper objectMapper, JwtProperties jwtProperties, PasswordEncoder passwordEncoder, TokenManager tokenManager, UserDetailService userDetailService) {
        this.objectMapper = objectMapper;
        this.jwtProperties = jwtProperties;
        this.passwordEncoder = passwordEncoder;
        this.tokenManager = tokenManager;
        this.userDetailService = userDetailService;
    }

    @SuppressWarnings("unchecked")
    @Bean
    @ConditionalOnMissingFilterBean(TokenFilter.class)
    public FilterRegistrationBean<TokenFilter> tokenFilter(DenyHandler denyHandler, UnauthorizedHandler unauthorizedHandler, AuthorityMatcher authorityMatcher) {
        Class<? extends UserDetailService> userDetailServiceClass = userDetailService.getClass();
        Class<? extends UserDetails> userClass;
        try {
            Class<?> type = userDetailServiceClass.getMethod("loadUserByUsername", String.class).getReturnType();
            userClass = (Class<? extends UserDetails>) type;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("无法找到UserDetails的实现类");
        }
        FilterRegistrationBean<TokenFilter> bean = new FilterRegistrationBean<>();
        TokenFilter filter = new TokenFilter(objectMapper, jwtProperties, tokenManager, authorityMatcher, denyHandler, unauthorizedHandler, userClass);
        bean.setFilter(filter);
        bean.setName(filter.getName());
        bean.setOrder(2);
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    @ConditionalOnMissingFilterBean(LoginFilter.class)
    public FilterRegistrationBean<LoginFilter> LoginFilter(AuthenticationDenyHandler authenticationDenyHandler,
                                                           AuthenticationSuccessHandler authenticationSuccessHandler) {
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>();
        LoginFilter filter = new LoginFilter(objectMapper, jwtProperties, passwordEncoder, tokenManager, userDetailService, authenticationDenyHandler, authenticationSuccessHandler);
        bean.setFilter(filter);
        bean.setName(filter.getName());
        bean.setOrder(1);
        bean.addUrlPatterns(jwtProperties.getLogin().getLoginUri());
        return bean;
    }
}
