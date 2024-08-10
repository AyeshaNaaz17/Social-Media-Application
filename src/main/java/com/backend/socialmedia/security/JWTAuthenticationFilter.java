package com.backend.socialmedia.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.socialmedia.services.UserDetailsServiceImplementation;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter { 

    private final JWTTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImplementation userDetailsService;

    @Autowired
    public JWTAuthenticationFilter(JWTTokenProvider jwtTokenProvider, UserDetailsServiceImplementation userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = extractJwtFromRequest(request);  
        if (StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) {        
            try {
                Long id = jwtTokenProvider.getUserIdFromJWT(jwtToken);               
                UserDetails user = userDetailsService.loadUserById(id); 
                if (user != null) {                               
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);              
                }
            } catch (Exception e) {
                // Log the exception
                logger.error("Failed to authenticate user", e);
            }
        }
        filterChain.doFilter(request, response);                         
    }

    private String extractJwtFromRequest(HttpServletRequest request) {                                     
        String bearer = request.getHeader("Authorization");                  
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {  
            return bearer.substring("Bearer ".length());
        }
        return null;
    }
}
