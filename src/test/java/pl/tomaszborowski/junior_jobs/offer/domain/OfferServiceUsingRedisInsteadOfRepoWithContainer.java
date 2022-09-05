package pl.tomaszborowski.junior_jobs.offer.domain;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.tomaszborowski.junior_jobs.JuniorJobsApplication;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = OfferServiceUsingRedisInsteadOfRepoWithContainer.Config.class)
@ActiveProfiles("redistest")
@Testcontainers
public class OfferServiceUsingRedisInsteadOfRepoWithContainer {

    public static final String CACHE_COLLECTION_NAME = "jobOffersCached";
    @MockBean
    OfferRepo offerRepo;

    @Container
    private static final GenericContainer<?> redisContainer = new GenericContainer<>("redis")
            .withExposedPorts(6379);

    static {
        redisContainer.start();
        Integer redisPort = redisContainer.getFirstMappedPort();
        System.setProperty("REDIS_TEST_PORT", String.valueOf(redisPort));
    }

    @Test
    public void whenFindAllServiceMethodInvoked_thenRedisCacheManagerShouldContainThoseItems(@Autowired CacheManager cacheManager,
                                                                                             @Autowired OfferService offerService){
        //when
        offerService.findAllOffers();

        //then
        Assertions.assertThat(cacheManager.getCacheNames().contains(CACHE_COLLECTION_NAME));
    }

    @Test
    public void whenFindAllInvokedThreeTimesPer11Seconds_shouldUseDB_Redis_DB(@Autowired OfferService offerService) {

        //given
        int expectedTimesOfInvocationOfRepo = 2;
        int secondsOfRedisTesting = 10;
        Duration duration = Duration.ofSeconds(secondsOfRedisTesting);

        //when
        //then

        await()
                .atMost(duration)
                .untilAsserted(() -> {
                            offerService.findAllOffers();
                            verify(offerRepo, times(expectedTimesOfInvocationOfRepo)).findAll();
                        }
                );


    }
    @Test
    public void whenFindAllInvokedTwoTimes_shouldUseDB_Redis(@Autowired CacheManager cacheManager,
                                                             @Autowired OfferService offerService) throws InterruptedException {
        //given
        final int expectedTimesOfInvocation = 1;
        assertThat(cacheManager.getCacheNames().isEmpty()).isTrue();

        //when
        offerService.findAllOffers();
        Thread.sleep(200);
        offerService.findAllOffers();

        assertThat(cacheManager.getCacheNames().contains(CACHE_COLLECTION_NAME));
        verify(offerRepo, times(expectedTimesOfInvocation)).findAll();
    }


    @Import(JuniorJobsApplication.class)
    static class Config{
    }
}
