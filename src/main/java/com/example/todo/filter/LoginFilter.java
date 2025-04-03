package com.example.todo.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter implements Filter {

    // 로그인 없이 접근 가능한 화이트 리스ㅡ트
    private static final String[] WHITE_LIST = {"/", "/members/login", "/members/signup", "/members/logout"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        // 화이트 리스트에 없을 경우 검증
        if (!isWhiteList(requestURI)) {

            HttpSession session = httpRequest.getSession(false);

            // 세션 정보가 없으면 에러
            if (session == null || session.getAttribute("sessionKey") == null) {
                sendUnauthorizedResponse(httpResponse);
                return;
            }
        }

        // 다음 필터로 
        filterChain.doFilter(servletRequest, servletResponse);
    }

    // 화이트 리스트에 포함되는지 검증
    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }

    // 인증되지 않은 요청에 401 반환
    private void sendUnauthorizedResponse(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        servletResponse.setContentType("application/json");
        servletResponse.setCharacterEncoding("UTF-8");

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", "Unauthorized");
        responseBody.put("message", "로그인해주세요");

        String json = new ObjectMapper().writeValueAsString(responseBody);
        servletResponse.getWriter().write(json);
    }
}
