package com.danacup.interceptor;

import com.danacup.annotation.Auth;
import com.danacup.exception.ApiError;
import com.danacup.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.getBean().getClass().isAnnotationPresent(Auth.class) ||
                    handlerMethod.hasMethodAnnotation(Auth.class)) {

                try {
                    String authHeader = request.getHeader("Authorization");
                    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                        throw new ApiError.Unauthorized("Missing or invalid Authorization header");
                    }

                    var token = authHeader.split("Bearer ")[1].trim();
                    if (token.isEmpty()) {
                        throw new ApiError.Unauthorized("Empty token");
                    }

                    var user = authService.validateToken(token);
                    request.setAttribute("user", user);
                } catch (ResponseStatusException e) {
                    throw new ApiError.Unauthorized("Authentication failed");
                }
            }
        }
        return true;
    }
}
