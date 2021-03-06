package cn.graydove.security.handler.support;


import cn.graydove.security.exception.FailException;
import cn.graydove.security.handler.UnauthorizedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 未登录
 */
public class DefaultUnauthorizedHandler extends UnauthorizedHandler {


    @Override
    public void doHandle(HttpServletRequest request, HttpServletResponse response, FailException e) throws IOException, ServletException {
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(e.getMessage());
    }
}
