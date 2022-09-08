package pl.tomaszborowski.junior_jobs.security.login;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

public interface SampleAuthentication {
    default UsernamePasswordAuthenticationToken sampleAuthentication(User user) {
        return new UsernamePasswordAuthenticationToken(user, null);
    }
}
