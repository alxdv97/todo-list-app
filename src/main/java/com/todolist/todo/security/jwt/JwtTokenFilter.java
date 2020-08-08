package com.todolist.todo.security.jwt;

import com.todolist.todo.security.jwt.blacklist.JwtBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider tokenProvider;

    @Autowired
    private JwtBlacklistService blacklistService;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        String token = tokenProvider.resolveToken((HttpServletRequest) servletRequest);

        if (token != null
                && tokenProvider.validateToken(token)
                && !blacklistService.isInBlacklist(token)) {//TODO: add check in blacklist

            Authentication authentication = tokenProvider.getAuthentication(token);

            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);//authenticate request
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
