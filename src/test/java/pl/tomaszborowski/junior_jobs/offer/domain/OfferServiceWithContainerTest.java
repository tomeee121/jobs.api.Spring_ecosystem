package pl.tomaszborowski.junior_jobs.offer.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.tomaszborowski.junior_jobs.JuniorJobsApplication;
import pl.tomaszborowski.junior_jobs.offer.domain.Dao.Offer;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(classes = JuniorJobsApplication.class)
@ActiveProfiles("container")
@Testcontainers
public class OfferServiceWithContainerTest implements OfferSamples {

    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo");

     static {
        mongoDBContainer.start();
        Integer port = mongoDBContainer.getFirstMappedPort();
        System.setProperty("MONGO_PORT", String.valueOf(port));
    }

    @Test
    public void whenFindAllServiceMethodInvoked_ShouldReturnTwoElements(@Autowired OfferService offerService,
                                                                        @Autowired OfferRepo offerRepo) {

    Offer cybersourceOffer = cybersourceOffer();
    Offer cdq = cdqPolandOffer();
    then(offerRepo.findAll()).containsAll(Arrays.asList(cybersourceOffer, cdq));

    final List<OfferDto> allOffers = offerService.findAllOffers();
    }


}
