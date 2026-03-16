package com.DhrubaStudio.journalApp.config;

import com.DhrubaStudio.journalApp.service.UserDetailsServiceImpl;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter{

    private final JwtUtilities jwtUtilities;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    JwtFilter(JwtUtilities jwtUtilities, UserDetailsServiceImpl userDetailsService){
        this.jwtUtilities = jwtUtilities;
        this.userDetailsService = userDetailsService;
    }

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String username = null;
        String jwt = null;
        if(authorization != null && authorization.startsWith("Bearer ")){
            jwt = authorization.substring(7);
            try{
                username = jwtUtilities.extractUsername(jwt);
            }catch(ExpiredJwtException e){
                log.warn("JWT Token has expired: {}",e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Your session has expired, please log in again.");
                return;
            }catch(JwtException e){
                log.warn("Invalid JWT Token: {}",e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Authentication failed, please log in again.");
                return;
            }
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}

