package com.ayushsingh.assessmentportal.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.ayushsingh.assessmentportal.service.service_impl.UserDetailsServiceImpl;
@Configuration//this tells that this a configuration class
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//this will be used for role based authentication
public class SecurityConfig {
    
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
   
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
   
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;
    
    private final String [] PUBLIC_URLS={
        "/assessmentportal/authenticate/**",
        "/assessmentportal/authenticate/verifyemail/verify-otp"
    };
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      
        http.csrf()
        .disable()
        // .cors()
        // .disable()
        .authorizeHttpRequests()
        .antMatchers(PUBLIC_URLS)
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS)//permit all the options methods
        .permitAll()
        .anyRequest()
        .authenticated()//authenticate any other request
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(this.unauthorizedHandler)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//make the session creation policy stateless
        //add a filter which checks the token
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //load user by username method will be called from userDetailsService
        provider.setUserDetailsService(this.userDetailsServiceImpl);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
