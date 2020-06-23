package cn.graydove.security.config;

import cn.graydove.security.crypto.PasswordEncoder;
import cn.graydove.security.filter.AuthorizationFilter;
import cn.graydove.security.filter.VerificationFilter;
import cn.graydove.security.handler.AuthenticationDenyHandler;
import cn.graydove.security.handler.AuthenticationSuccessHandler;
import cn.graydove.security.handler.DenyHandler;
import cn.graydove.security.handler.UnauthorizedHandler;
import cn.graydove.security.token.TokenManager;
import cn.graydove.security.properties.JwtProperties;
import cn.graydove.security.token.getter.TokenGetter;
import cn.graydove.security.token.manager.AuthorityMatcher;
import cn.graydove.security.token.setter.CookieTokenSetter;
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
    @ConditionalOnMissingFilterBean(VerificationFilter.class)
    public FilterRegistrationBean<VerificationFilter> tokenFilter(DenyHandler denyHandler, UnauthorizedHandler unauthorizedHandler, AuthorityMatcher authorityMatcher, TokenGetter tokenGetter) {
        Class<? extends UserDetailService> userDetailServiceClass = userDetailService.getClass();
        Class<? extends UserDetails> userClass;
        try {
            Class<?> type = userDetailServiceClass.getMethod("loadUserByUsername", String.class).getReturnType();
            userClass = (Class<? extends UserDetails>) type;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("无法找到UserDetails的实现类");
        }
        FilterRegistrationBean<VerificationFilter> bean = new FilterRegistrationBean<>();
        VerificationFilter filter = new VerificationFilter(objectMapper, jwtProperties, tokenManager, authorityMatcher, denyHandler, unauthorizedHandler, tokenGetter, userClass);
        bean.setFilter(filter);
        bean.setName(filter.getName());
        bean.setOrder(101);
        bean.addUrlPatterns("/*");
        return bean;
    }

    @Bean
    @ConditionalOnMissingFilterBean(AuthorizationFilter.class)
    public FilterRegistrationBean<AuthorizationFilter> LoginFilter(AuthenticationDenyHandler authenticationDenyHandler,
                                                                   AuthenticationSuccessHandler authenticationSuccessHandler,
                                                                   CookieTokenSetter cookieTokenSetter) {
        FilterRegistrationBean<AuthorizationFilter> bean = new FilterRegistrationBean<>();
        AuthorizationFilter filter = new AuthorizationFilter(objectMapper, jwtProperties, passwordEncoder, tokenManager, userDetailService, authenticationDenyHandler, authenticationSuccessHandler);
        if (jwtProperties.getCookie().isEnable()) {
            filter.setCookieTokenSetter(cookieTokenSetter);
        }
        bean.setFilter(filter);
        bean.setName(filter.getName());
        bean.setOrder(100);
        bean.addUrlPatterns(jwtProperties.getLogin().getLoginUri());
        return bean;
    }
}
