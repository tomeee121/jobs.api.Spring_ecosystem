package pl.tomaszborowski.junior_jobs.offer.scheduling;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import pl.tomaszborowski.junior_jobs.JuniorJobsApplication;
import pl.tomaszborowski.junior_jobs.infrastructure.RemoteOfferClient;

import java.time.Duration;

import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = JuniorJobsApplication.class)
@ActiveProfiles("schedulertest")
class HttpOfferClientSchedulerTest {

    @SpyBean
    private RemoteOfferClient httpOfferClient;

    @Test
    public void whenWaited11Seconds_thenScheduledIsCalledTwoTimes(){

        await().atMost(Duration.ofSeconds(11))
                .untilAsserted(() -> Mockito.verify(httpOfferClient, times(2)).getOffers());
    }

}