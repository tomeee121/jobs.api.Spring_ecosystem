package pl.tomaszborowski.junior_jobs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import pl.tomaszborowski.junior_jobs.security.login.SecurityContextUpdater;
import pl.tomaszborowski.junior_jobs.security.login.domain.AuthEntryPoint;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dao.UserRepo;

@Configuration
public class JwtConfig {

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthEntryPoint();
    }

    @Bean
    public JwtUtils jwtUtils(@Value("${offers.jwt.secret:test}") String jwtSecret,
                             @Value("${offers.jwt.expiration.time.Mseconds}") int expirationTimeMSeconds) {
        return new JwtUtils(jwtSecret, expirationTimeMSeconds);
    }


    @Bean
    public UserDetailsService userDetailsService(@Autowired UserRepo userRepo) {
        return new pl.tomaszborowski.junior_jobs.security.login.domain.UserDetailsService(userRepo);
    }

    @Bean
    public AuthTokenFilter authTokenFilter   (@Autowired JwtUtils jwtUtils,
                                              @Autowired UserDetailsService userDetailsService,
                                              @Autowired SecurityContextUpdater securityContextUpdater) {
        return new AuthTokenFilter(jwtUtils, userDetailsService, securityContextUpdater);
    }

    @Bean
    public SecurityContextUpdater setAuthentication() {
        return new SecurityContextUpdater();
    }
}
