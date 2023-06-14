package com.ayushsingh.assessmentportal.configs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.ayushsingh.assessmentportal.constants.AppConstants;
import com.ayushsingh.assessmentportal.service.service_impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import com.ayushsingh.assessmentportal.exceptions.InvalidTokenInHeaderException;

/*
 * Class to authorize the token in the header
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final String CLASS_NAME = JwtAuthenticationFilter.class.getSimpleName();
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            // Step 1: Get authorization header
            final String authorizationHeader = request.getHeader(AppConstants.AUTHORIZATION);
            System.out.println(CLASS_NAME + " request header token: " +
                    authorizationHeader);

            // Step 2: Extract username and token
            String username = null, jwtToken = null;
            if (authorizationHeader != null &&
                    authorizationHeader.startsWith(AppConstants.BEARER)) {

                jwtToken = authorizationHeader.substring(7);
                /*
                 * Handle following errors for extractUsername method
                 * ExpiredJwtException,
                 * UnsupportedJwtException,
                 * MalformedJwtException,
                 * SignatureException,
                 * IllegalArgumentException
                 */
                username = this.jwtUtil.extractUsername(jwtToken);
                // Extracted username
                System.out.println("Username: " + username);
                System.out.println("getAuthentication: " +
                        SecurityContextHolder.getContext().getAuthentication());
                // Step 3: Check if username is not null and getAuthentication gives null
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Step 4: Extract the userDetails
                    final UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(username);

                    // Step 5: Validate the jwt token with user details
                    if (this.jwtUtil.validateToken(jwtToken, userDetails)) {
                        // Step 6: If token is valid set authentication in security context holder
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    } else {
                        System.out.println(CLASS_NAME + " jwt token validation returned false");
                    }
                } else {
                    exceptionResolver.resolveException(request, response, null,
                            new InvalidTokenInHeaderException(
                                    "username is null: " + (username == null) + " authentication in context is null: "
                                            + (SecurityContextHolder.getContext().getAuthentication())));
                }
            } else {
                System.out.println("Header token is null or does not start with BEARER");
            }
            // Step 7: Proceed
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            exceptionResolver.resolveException(request, response, null,
                    new InvalidTokenInHeaderException("Token is expired"));
        } catch (UnsupportedJwtException e) {
            exceptionResolver.resolveException(request, response, null,
                    new InvalidTokenInHeaderException(e.getMessage()));
        } catch (MalformedJwtException e) {
            exceptionResolver.resolveException(request, response, null,
                    new InvalidTokenInHeaderException(e.getMessage()));
        } catch (SignatureException e) {
            exceptionResolver.resolveException(request, response, null,
                    new InvalidTokenInHeaderException(e.getMessage()));
        } catch (IllegalArgumentException e) {
            exceptionResolver.resolveException(request, response, null,
                    new InvalidTokenInHeaderException(e.getMessage()));
        } catch (InvalidTokenInHeaderException e) {
            exceptionResolver.resolveException(request, response, null, e);
        }
    }

}
