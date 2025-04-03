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

    private static final String[] WHITE_LIST = {"/", "/members/login", "/members/signup", "/members/logout"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if (!isWhiteList(requestURI)) {

            HttpSession session = httpRequest.getSession(false);

            if (session == null || session.getAttribute("sessionKey") == null) {
                // throw new RuntimeException("로그인 해주세요");
                sendUnauthorizedResponse(httpResponse);
                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }

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
