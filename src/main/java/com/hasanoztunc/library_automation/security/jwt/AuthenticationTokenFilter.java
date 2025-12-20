package com.hasanoztunc.library_automation.security.jwt;

import com.hasanoztunc.library_automation.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

    private JwtUtilities jwtUtilities;
    private UserDetailsServiceImpl userDetailsService;

    public AuthenticationTokenFilter(
            JwtUtilities jwtUtilities,
            UserDetailsServiceImpl userDetailsService
    ) {
        this.jwtUtilities = jwtUtilities;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            var jwt = jwtUtilities.getJwtCookies(request);

            if (jwt != null && jwtUtilities.validateJwtToken(jwt)) {
                var username = jwtUtilities.getUsernameFromJwtToken(jwt);
                var userDetails = userDetailsService.loadUserByUsername(username);

                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authentication
                        .setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);
            }
        } catch (Exception exception) {
            logger.error("Cannot set user authentication: {}", exception.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}