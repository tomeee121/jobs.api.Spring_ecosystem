package pl.tomaszborowski.junior_jobs.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    public static final String AUTH_HEADER_FIRST_7_LETTERS = "Bearer ";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    
    JwtUtils jwtUtils;
    AuthenticationManager authenticationManager;

    @Autowired
    public AuthTokenFilter(JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


            }

        }


    }
}
