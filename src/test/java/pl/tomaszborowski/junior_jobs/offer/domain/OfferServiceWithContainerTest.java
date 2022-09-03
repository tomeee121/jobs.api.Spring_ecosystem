package pl.tomaszborowski.junior_jobs.offer.domain;

import org.assertj.core.api.Assertions;
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
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(classes = JuniorJobsApplication.class)
@ActiveProfiles("container")
@Testcontainers
public class OfferServiceWithContainerTest implements OfferSamples, OfferDtoSamples {

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
    //given
    Offer cybersourceOffer = cybersourceOffer();
    Offer cdq = cdqPolandOffer();
    then(offerRepo.findAll()).containsAll(Arrays.asList(cybersourceOffer, cdq));

    //when
    final List<OfferDto> actual = offerService.findAllOffers();

    //then
    assertThat(actual).containsExactlyInAnyOrderElementsOf(Arrays.asList(cybersourceDtoOffer(), cdqPolandDtoOffer()));

    }

    @Test
    void whenFindByIdServiceMethodInvoked_RepoShouldReturnOneElement(@Autowired OfferRepo offerRepo,
                                                                     @Autowired OfferService service) {
        //given
        OfferDto cybersourceOfferDto = cybersourceDtoOffer();
        then(offerRepo.findById("63073c6c2db2415cbc03afab")).isPresent();

        //when
        final OfferDto actual = service.findOfferById("63073c6c2db2415cbc03afab");

        //then
        assertThat(actual).isEqualTo(cybersourceOfferDto);
    }

    @Test
    void whenInRepoFindOfferWithNotExistingIdInvoked_ThenShouldThrowOfferNotFoundException(@Autowired OfferRepo offerRepo,
                                                                                           @Autowired OfferService service) {
        //when
        //then
        Assertions.assertThatThrownBy(() -> service.findOfferById("zxc"))
                .isInstanceOf(OfferNotFoundException.class)
                .hasMessage("Offer with id of zxc was not found!");
    }


}
