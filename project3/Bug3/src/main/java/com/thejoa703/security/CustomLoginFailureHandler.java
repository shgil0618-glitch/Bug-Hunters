package com.thejoa703.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {

        HttpSession session = request.getSession();

        // 기본 메시지 (비밀번호 틀림 등)
        String errorMessage = "아이디 또는 비밀번호를 확인하세요.";

        // 🔥 실제 원인 예외 (UserDetailsService에서 던진 것)
        Throwable cause = exception.getCause();

        // 🔒 계정 정지
        if (cause instanceof DisabledException) {
            errorMessage = cause.getMessage();
        }

        // 세션에 메시지 저장
        session.setAttribute("errorMessage", errorMessage);

        // 로그인 페이지로 리다이렉트
        response.sendRedirect("/users/login");
    }
}