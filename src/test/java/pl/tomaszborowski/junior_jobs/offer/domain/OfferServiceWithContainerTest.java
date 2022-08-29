package pl.tomaszborowski.junior_jobs.offer.domain;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import pl.tomaszborowski.junior_jobs.JuniorJobsApplication;

@SpringBootTest(classes = JuniorJobsApplication.class)
@Profile("container")
@Testcontainers
public class OfferServiceWithContainerTest {

    @Container
    private final static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo");

    static {
        mongoDBContainer.start();
        Integer port = mongoDBContainer.getFirstMappedPort();
        System.setProperty("DB_PORT", String.valueOf(port));
    }


}
