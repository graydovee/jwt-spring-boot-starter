package cn.graydove.security.handler;

import cn.graydove.security.exception.FailException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractFailHandler implements FailHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, FailException e) throws IOException, ServletException {
        response.setStatus(getCode());
        doHandle(request, response, e);
    }

    public abstract void doHandle(HttpServletRequest request, HttpServletResponse response, FailException e) throws IOException, ServletException;

    public abstract int getCode();
}
