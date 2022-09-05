package pl.tomaszborowski.junior_jobs.offer.domain;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.tomaszborowski.junior_jobs.JuniorJobsApplication;
import pl.tomaszborowski.junior_jobs.offer.domain.Dao.Offer;

import java.util.Arrays;

@SpringBootTest(classes = JuniorJobsApplication.class)
@ActiveProfiles("container")
@Testcontainers
public class OfferServiceSaveAllFromHttpClientWithContainerTests implements OfferSamples, OfferDtoSamples{

    @Container
    private static final MongoDBContainer mongo = new MongoDBContainer("mongo");
    
    static{
        mongo.start();
        Integer firstMappedPort = mongo.getFirstMappedPort();
        System.setProperty("DB_PORT", String.valueOf(firstMappedPort));
    }

    @Test
    void whenHttpOfferClientSchedulerInvoked_thenShouldAddNotExistingBeforeOffers(@Autowired OfferRepo offerRepo,
                                                                                  @Autowired OfferService offerService){
        //given
        final Offer addeableOffer1 = createOffer("NOT_UNIQUE",
                "urlsecondTest",
                "positionsecondTest",
                "10k");
        final pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDto addeableOffer2 = createHttpClientOfferDto("UNIQUE",
                "urlfirstTest",
                "positionfirstTest",
                "12k");
        final pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDto existingOffer = createHttpClientOfferDto("NOT_UNIQUE",
                "urlThirdTest",
                "positionfirstTest",
                "10k");

        //when
        offerRepo.save(addeableOffer1);
        BDDAssertions.then(offerRepo.findAll().size()).isEqualTo(1);
        BDDAssertions.then(offerRepo.existsByOfferUrl(addeableOffer1.getOfferUrl())).isTrue();

        //then
        offerService.saveOffersDirectlyFromHttpClient(Arrays.asList(addeableOffer2, existingOffer));
        BDDAssertions.then(offerRepo.findAll().size()).isEqualTo(2);
        BDDAssertions.then(offerRepo.existsByOfferUrl(addeableOffer2.getOfferUrl())).isTrue();

    }

    
}
