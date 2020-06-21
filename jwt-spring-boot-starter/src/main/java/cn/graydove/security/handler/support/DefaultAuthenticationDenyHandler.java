package cn.graydove.security.handler.support;

import cn.graydove.security.exception.FailException;
import cn.graydove.security.handler.AuthenticationDenyHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败
 */
public class DefaultAuthenticationDenyHandler extends AuthenticationDenyHandler {

    @Override
    public void doHandle(HttpServletRequest request, HttpServletResponse response, FailException e) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(e.getMessage());
    }
}
