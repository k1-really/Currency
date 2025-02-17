package com.kireally.Currency.web.security;

import com.kireally.Currency.exception.ResourceNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;


@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String bearerToken = ((HttpServletRequest)servletRequest).getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            bearerToken = bearerToken.substring(7);
        }
        System.out.println("Token" + bearerToken);
        if(bearerToken != null && jwtTokenProvider.validateToken(bearerToken)) {
            try{
                System.out.println("1");
                Authentication auth = jwtTokenProvider.getAuthentication(bearerToken);
                System.out.println("2");
                if(auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    System.out.println("3");
                }
            } catch(ResourceNotFoundException ignored){}
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
