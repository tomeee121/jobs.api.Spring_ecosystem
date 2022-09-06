package pl.tomaszborowski.junior_jobs.security.login.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dao.User;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dao.UserRepo;

import java.util.Collections;

@RequiredArgsConstructor
@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepo userRepo;
    private static final String USER_NOT_FOUND = "User not found";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
        return userDetails;

    }
}
