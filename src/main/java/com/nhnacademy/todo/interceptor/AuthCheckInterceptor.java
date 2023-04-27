package com.nhnacademy.todo.interceptor;

import com.nhnacademy.todo.exception.UnauthorizedUserException;
import com.nhnacademy.todo.share.UserIdStore;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = request.getHeader("X-USER-ID");
        if(!StringUtils.hasText(userId)){
            throw new UnauthorizedUserException();
        }
        UserIdStore.setUserId(userId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserIdStore.remove();
    }

}
