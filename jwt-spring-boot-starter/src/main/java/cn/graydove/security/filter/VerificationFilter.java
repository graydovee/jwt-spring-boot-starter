package cn.graydove.security.filter;


import cn.graydove.security.exception.DenyException;
import cn.graydove.security.exception.InvalidTokenException;
import cn.graydove.security.exception.UnauthorizedException;
import cn.graydove.security.handler.DenyHandler;
import cn.graydove.security.handler.UnauthorizedHandler;
import cn.graydove.security.properties.JwtProperties;
import cn.graydove.security.token.TokenManager;
import cn.graydove.security.token.getter.TokenGetter;
import cn.graydove.security.token.manager.AuthorityMatcher;
import cn.graydove.security.userdetails.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class VerificationFilter extends BaseFilter {

    private ObjectMapper objectMapper;

    private JwtProperties jwtProperties;

    private TokenManager tokenManager;

    private AuthorityMatcher authorityMatcher;

    private DenyHandler denyHandler;

    private UnauthorizedHandler unauthorizedHandler;

    private TokenGetter tokenGetter;

    private Class<? extends UserDetails> userClass;

    public VerificationFilter(ObjectMapper objectMapper, JwtProperties jwtProperties, TokenManager tokenManager, AuthorityMatcher authorityMatcher, DenyHandler denyHandler, UnauthorizedHandler unauthorizedHandler, TokenGetter tokenGetter, Class<? extends UserDetails> userClass) {
        this.objectMapper = objectMapper;
        this.jwtProperties = jwtProperties;
        this.tokenManager = tokenManager;
        this.authorityMatcher = authorityMatcher;
        this.denyHandler = denyHandler;
        this.unauthorizedHandler = unauthorizedHandler;
        this.tokenGetter = tokenGetter;
        this.userClass = userClass;
    }

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        UserDetails user;
        try {
            String token = tokenGetter.getToken(request, jwtProperties.getToken());
            user = check(token);
            request.setAttribute(UserDetails.USER_PARAM_NAME, user);

            if (!authorityMatcher.match(requestURI).asserts(user)) {
                throw new DenyException();
            }
        } catch (InvalidTokenException | UnauthorizedException e) {
            if (!authorityMatcher.match(requestURI).asserts(null)) {
                unauthorizedHandler.handle(request, response, e);
                return;
            }
        } catch (DenyException e) {
            denyHandler.handle(request, response, e);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private UserDetails check(String token) throws InvalidTokenException, UnauthorizedException {
        if (token == null) {
            throw new UnauthorizedException();
        }
        String userJsonInfo = tokenManager.parseToken(token);
        if (userJsonInfo == null) {
            throw new InvalidTokenException();
        }
        try {
            return objectMapper.readValue(userJsonInfo, userClass);
        } catch (Exception e) {
            log.warn("user序列化失败：" + e.getMessage(), e);
            throw new InvalidTokenException();
        }
    }


    @Override
    public String getName() {
        return "TOKEN_FILTER";
    }

}
