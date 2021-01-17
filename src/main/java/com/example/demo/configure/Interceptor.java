package com.example.demo.configure;

import com.example.demo.services.JwtService;
import io.jsonwebtoken.Jwt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Interceptor implements HandlerInterceptor {

    @Autowired
    private JwtService JwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("jwt-auth-token");
        if(StringUtils.isNotBlank(token)){
            JwtService.checkJwt(token); //유효한 토큰이 아니면 예외 발생 시킴
            return true;
        }

        return false;
    }
}
