package com.ayushsingh.assessmentportal.configs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ayushsingh.assessmentportal.constants.AppConstants;
import com.ayushsingh.assessmentportal.service.service_impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Get authorization header
        final String requestTokenHeader = request.getHeader(AppConstants.AUTHORIZATION);
        System.out.println(CLASS_NAME + " request header token: " + requestTokenHeader);
        String username = null;
        String jwtToken = null;
        // try {

            if (requestTokenHeader != null && requestTokenHeader.startsWith(AppConstants.BEARER)) {

                try {
                    jwtToken = requestTokenHeader.substring(7);
                    username = this.jwtUtil.extractUsername(jwtToken);
                } catch (ExpiredJwtException e) {
                    // System.out.println(CLASS_NAME + " jwt token has expired");
                    // e.printStackTrace();
                    // throw new InvalidTokenInHeaderException(jwtToken, "ExpiredJwtToken exception:
                    // ");
                }
            } else {
                System.out.println(CLASS_NAME + " jwt token is either empty or does not starts with Bearer");
                // throw new InvalidTokenInHeaderException(requestTokenHeader,
                // "Either token is null or does not starts with Bearer");
            }
            // now validate the token
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                final UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(username);

                if (this.jwtUtil.validateToken(jwtToken, userDetails)) {
                    // token is valid
                    // set authentication in security context holder
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    System.out.println(CLASS_NAME + " jwt token validation returned false");
                    // throw new InvalidTokenInHeaderException(jwtToken, "token validation returned
                    // false");
                }
            } else {
                System.out.println(CLASS_NAME + " username is null username: " + username
                        + " or context authentication is not null");
                // throw new InvalidTokenInHeaderException(jwtToken, "either username is null or
                // security context is not null (username: "+username+")");
            }
        // } catch (ExpiredJwtException ex) {
        //     System.out.println(CLASS_NAME + "JWT token has expired");
        //     String isRefreshToken = request.getHeader("isRefreshToken");
        //     String requestURL = request.getRequestURL().toString();
        //     // allow for Refresh Token creation if following conditions are true.
        //     if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
        //         allowForRefreshToken(ex, request);
        //     } else
        //         request.setAttribute("exception", ex);

        // } catch (BadCredentialsException ex) {
        //     request.setAttribute("exception", ex);
        // } catch (Exception ex) {
        //     System.out.println(ex);
        // }
        filterChain.doFilter(request, response);
    }

    // // new
    // private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

    //     // create a UsernamePasswordAuthenticationToken with null values.
    //     UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
    //             null, null, null);
    //     // After setting the Authentication in the context, we specify
    //     // that the current user is authenticated. So it passes the
    //     // Spring Security Configurations successfully.
    //     SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    //     // Set the claims so that in controller we will be using it to create
    //     // new JWT
    //     request.setAttribute("claims", ex.getClaims());

    // }
}
