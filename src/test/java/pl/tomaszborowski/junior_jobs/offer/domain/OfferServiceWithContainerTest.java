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
        OfferDto cybersourceOfferDto = cybersourceDtoOffer();
        OfferDto cdqDto = cdqPolandDtoOffer();
        List<OfferDto> offersDto = Arrays.asList(cdqDto, cybersourceOfferDto);

        List<Offer> offers = offerRepo.findAll();
        assertThat(offers).usingElementComparatorIgnoringFields("id").containsExactlyInAnyOrderElementsOf(Arrays.asList(cybersourceOffer, cdq));

    //when
    final List<OfferDto> actual = offerService.findAllOffers();

    //then
    assertThat(actual).usingElementComparatorIgnoringFields("id").containsExactlyInAnyOrderElementsOf(offersDto);
    assertThat(actual.size()).isEqualTo(2);


    }

    @Test
    void whenFindByIdServiceMethodInvoked_RepoShouldReturnOneElement(@Autowired OfferRepo offerRepo,
                                                                     @Autowired OfferService service) {
        //given
        OfferDto expectedOfferDto;
        List<Offer> offers = offerRepo.findAll();
        String idOfOfferSaved;
        if(offers.get(0).getCompany().equals("Cybersource")){
            idOfOfferSaved = offers.stream().filter(company -> company.getCompany().equals("Cybersource")).findFirst().get().getId();
            expectedOfferDto = cybersourceDtoOffer();
        } else{
            idOfOfferSaved = offers.stream().filter(company -> company.getCompany().equals("CDQ Poland")).findFirst().get().getId();
            expectedOfferDto = cdqPolandDtoOffer();
        }

        then(offerRepo.findById(idOfOfferSaved)).isPresent();

        //when
        final OfferDto actual = service.findOfferById(idOfOfferSaved);

        //then
        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedOfferDto);
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
