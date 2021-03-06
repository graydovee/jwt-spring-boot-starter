package cn.graydove.security.handler;

import cn.graydove.security.exception.FailException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FailHandler {
    void handle(HttpServletRequest request, HttpServletResponse response, FailException e) throws IOException, ServletException;
}
