package cn.graydove.security.filter;

import cn.graydove.security.crypto.PasswordEncoder;
import cn.graydove.security.exception.ArgsNullException;
import cn.graydove.security.exception.UserNotEnableException;
import cn.graydove.security.exception.PasswordNotMatchException;
import cn.graydove.security.exception.UsernameNotFoundException;
import cn.graydove.security.handler.AuthenticationDenyHandler;
import cn.graydove.security.handler.AuthenticationSuccessHandler;
import cn.graydove.security.properties.CookieProperties;
import cn.graydove.security.properties.JwtProperties;
import cn.graydove.security.properties.TokenProperties;
import cn.graydove.security.token.Token;
import cn.graydove.security.token.TokenManager;
import cn.graydove.security.token.setter.CookieTokenSetter;
import cn.graydove.security.userdetails.UserDetailService;
import cn.graydove.security.userdetails.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class AuthorizationFilter extends BaseFilter {

    private ObjectMapper objectMapper;

    private JwtProperties jwtProperties;

    private PasswordEncoder passwordEncoder;

    private TokenManager tokenManager;

    private UserDetailService userDetailService;

    private AuthenticationDenyHandler authenticationDenyHandler;

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private CookieTokenSetter cookieTokenSetter;

    public AuthorizationFilter(ObjectMapper objectMapper, JwtProperties jwtProperties, PasswordEncoder passwordEncoder, TokenManager tokenManager, UserDetailService userDetailService, AuthenticationDenyHandler authenticationDenyHandler, AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.objectMapper = objectMapper;
        this.jwtProperties = jwtProperties;
        this.passwordEncoder = passwordEncoder;
        this.tokenManager = tokenManager;
        this.userDetailService = userDetailService;
        this.authenticationDenyHandler = authenticationDenyHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    public void setCookieTokenSetter(CookieTokenSetter cookieTokenSetter) {
        this.cookieTokenSetter = cookieTokenSetter;
    }

    @Override
    public String getName() {
        return "LOGIN_FILTER";
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!jwtProperties.getLogin().getMethod().equals(request.getMethod())) {
            filterChain.doFilter(request, response);
        }
        try {
            UserDetails user = check(request);
            user.setPassword(null);
            String jwt = tokenManager.createToken(objectMapper.writeValueAsString(user));

            TokenProperties tokenProperties = jwtProperties.getToken();
            Token token = new Token(tokenProperties.getType(), jwt);

            //写入Cookie
            CookieProperties cookieProperties = jwtProperties.getCookie();
            if (cookieProperties.isEnable() && TokenProperties.TYPE_BEARER.equals(token.getType())) {
                String bearerToken = URLEncoder.encode(cookieTokenSetter.getTokenValue(token), "utf-8");

                Cookie cookie = new Cookie(tokenProperties.getTokenKey(), bearerToken);
                cookie.setMaxAge((int) (tokenProperties.getTtl() / 1000));
                cookie.setSecure(cookieProperties.isSecure());
                cookie.setHttpOnly(cookieProperties.isHttpOnly());

                response.addCookie(cookie);
            }
            authenticationSuccessHandler.handle(request, response, token);
        } catch (ArgsNullException | UsernameNotFoundException | PasswordNotMatchException | UserNotEnableException e) {
            authenticationDenyHandler.handle(request, response, e);
        }
    }

    private UserDetails check(HttpServletRequest request) throws ArgsNullException, UsernameNotFoundException, PasswordNotMatchException, UserNotEnableException {
        String username = request.getParameter(jwtProperties.getLogin().getUsernameKeyName());
        String password = request.getParameter(jwtProperties.getLogin().getPasswordKeyName());
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new ArgsNullException();
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new PasswordNotMatchException();
        }
        if (!userDetails.isEnable()) {
            throw new UserNotEnableException();
        }
        return userDetails;
    }


}
