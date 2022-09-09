package pl.tomaszborowski.junior_jobs.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dao.User;
import pl.tomaszborowski.junior_jobs.security.login.domain.Dao.UserRepo;

@ChangeLog(order = "2")
@Profile("container")
public class DatabaseChangeLogTest {

    @ChangeSet(order = "001", id = "addITTestUser", author = "tomasz.borowski")
    @Profile("container")
    public void addUserToCollection(UserRepo userRepository, PasswordEncoder bCryptPasswordEncoder) {
        final User entity = new User();
        entity.setUsername("ExistentUser");
        entity.setPassword(bCryptPasswordEncoder.encode("password"));
        userRepository.insert(entity);
    }
}