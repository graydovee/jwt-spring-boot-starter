package cn.graydove.security.handler;

import cn.graydove.security.token.Token;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface AuthenticationSuccessHandler {
    void handle(HttpServletRequest request, HttpServletResponse response, Token token) throws IOException, ServletException;
}
