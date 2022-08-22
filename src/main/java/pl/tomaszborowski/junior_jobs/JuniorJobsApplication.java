package pl.tomaszborowski.junior_jobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class JuniorJobsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuniorJobsApplication.class, args);
    }

}
