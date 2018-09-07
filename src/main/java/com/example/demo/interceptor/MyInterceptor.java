package com.example.demo.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class MyInterceptor implements HandlerInterceptor{
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws IOException {
        HttpSession session = request.getSession();
//        判断是否已有该用户登录的session
        System.out.println("123123123123123");
        if(session.getAttribute("ucount") != null){
            return true;
        }
//        跳转到登录页
        String url = "/admin.html";
        response.sendRedirect(url);
        return false;
    }
}
