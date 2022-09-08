package pl.tomaszborowski.junior_jobs.offer.OfferControllerTest;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import pl.tomaszborowski.junior_jobs.security.AuthTokenFilter;
import pl.tomaszborowski.junior_jobs.security.JwtUtils;
import pl.tomaszborowski.junior_jobs.security.login.SecurityContextUpdater;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dao.UserRepo;
import pl.tomaszborowski.junior_jobs.security.login.domain.UserDetailsService;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestConfiguration
class JwtFilterTestConfig {

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return mock(AuthenticationEntryPoint.class);
    }
    @Bean
    JwtUtils jwtUtils() {
        final JwtUtils mock = mock(JwtUtils.class);
        when(mock.verifyToken(any())).thenReturn(true);
        when(mock.getUsernameFromToken(any())).thenReturn("user");
        return mock;
    }

    @Bean
    SecurityContextUpdater securityContextUpdater() {
        return new SecurityContextUpdater();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserRepo repoMock = mock(UserRepo.class);
        return new UserDetailsService(repoMock){
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return new User("user", "password", Collections.emptyList());
            }
        };
    }

    @Bean
    AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter(jwtUtils(), userDetailsService(), securityContextUpdater());
    }
}
