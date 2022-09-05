package pl.tomaszborowski.junior_jobs;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableMongock
public class JuniorJobsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuniorJobsApplication.class, args);
    }

}
