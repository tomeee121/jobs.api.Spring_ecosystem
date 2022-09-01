package pl.tomaszborowski.junior_jobs.offer.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.tomaszborowski.junior_jobs.JuniorJobsApplication;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(classes = JuniorJobsApplication.class)
@ActiveProfiles("container")
public class OfferServiceSaveAllWithContainerTests implements OfferSamples, OfferDtoSamples {

    @Container
    private static final MongoDBContainer DB_CONTAINER = new MongoDBContainer("mongo");

    static {
        DB_CONTAINER.start();
        System.setProperty("DB_PORT", String.valueOf(DB_CONTAINER.getFirstMappedPort()));
    }

    @Test
    public void whenInServiceSaveAllInvoked_ThenShouldAddTwoElementsToDB(@Autowired OfferService offerService) {
        //given
        List<OfferDto> offerDtos = Arrays.asList(cybersourceDtoOffer(), cdqPolandDtoOffer());
        int startNumberOfOfferDtos = offerService.findAllOffers().size();

        //when
        List<OfferDto> savedDtoOffers = offerService.saveOffers(offerDtos);
        int afterSaveOfferNumber = offerService.findAllOffers().size();

        //then
        assertThat(afterSaveOfferNumber).isGreaterThan(startNumberOfOfferDtos);
        assertThat(savedDtoOffers).containsAnyElementsOf(offerDtos);
    }
}
