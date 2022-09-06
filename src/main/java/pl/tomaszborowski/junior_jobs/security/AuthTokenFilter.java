package pl.tomaszborowski.junior_jobs.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.tomaszborowski.junior_jobs.security.login.domain.UserDetailsService;

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
    UserDetailsService userDetailsService;

    @Autowired
    public AuthTokenFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHOD)){
            response.setStatus(HttpStatus.OK.value());
        }
        else{
            String path = request.getRequestURI();
            if(path.startsWith("/login") || path.startsWith("/styles/**") || path.startsWith("/swagger-ui.html") || path.startsWith("/swagger-ui.html/**") ||
                    path.startsWith("/swagger-ui") || path.startsWith("/swagger-resources") || path.startsWith("/v3/api-docs") || path.startsWith("/v2/api-docs")){
                filterChain.doFilter(request, response);
                return;
            }
            else{
                String authHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
                if(authHeader == null || !authHeader.startsWith(AUTH_HEADER_FIRST_7_LETTERS)){
                    SecurityContextHolder.clearContext();
                    filterChain.doFilter(request, response);
                    return;
                }
                String token = request.getHeader(AUTHORIZATION_HEADER_NAME).substring(AUTH_HEADER_FIRST_7_LETTERS.length());
                boolean isJwtValid = jwtUtils.verifyToken(token);
                if(isJwtValid){
                    String usernameFromToken = jwtUtils.getUsernameFromToken(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetailsService, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                }
                else{
                    SecurityContextHolder.clearContext();
                }
                filterChain.doFilter(request, response);
            }

        }
    }
}
