package com.hasanoztunc.security.jwt;

import com.hasanoztunc.exceptions.BaseException;
import com.hasanoztunc.exceptions.ErrorMessage;
import com.hasanoztunc.enumerations.MessageType;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    // region Statics

    private final static String AUTHORIZATION_HEADER = "Authorization";

    // endregion

    // region Injections

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    // endregion

    // region Overrides

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);

            return;
        }

        try {
            String token = authorizationHeader.substring(7);
            String username = jwtService.getUserNameFromToken(token);

            if (username == null && SecurityContextHolder.getContext().getAuthentication() != null) {
                throw new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND, username));
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.checkIsTokenExpired(token)) {
                throw new BaseException(new ErrorMessage(MessageType.TOKEN_IS_EXPIRED, null));
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    userDetails.getAuthorities()
            );

            usernamePasswordAuthenticationToken.setDetails(userDetails);

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } catch (UsernameNotFoundException exception) {
            throw new BaseException(new ErrorMessage(MessageType.USERNAME_NOT_FOUND, null));
        } catch (ExpiredJwtException expiredJwtException) {
            throw new BaseException(new ErrorMessage(MessageType.TOKEN_IS_EXPIRED, null));
        } catch (Exception exception) {
            throw new BaseException(new ErrorMessage(MessageType.GENERAL_EXCEPTION, null));
        }

        filterChain.doFilter(request, response);
    }

    // region
}
