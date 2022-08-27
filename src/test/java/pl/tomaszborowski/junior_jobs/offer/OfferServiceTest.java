package pl.tomaszborowski.junior_jobs.offer;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import pl.tomaszborowski.junior_jobs.config.MongoOffersIDs;
import pl.tomaszborowski.junior_jobs.config.OfferControllerConfig;
import pl.tomaszborowski.junior_jobs.offer.Dto.OfferDtoSamples;
import pl.tomaszborowski.junior_jobs.offer.domain.Dao.Offer;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferRepo;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class OfferServiceTest implements OfferDtoSamples {


    OfferRepo mockOffersRepo;
    OfferService offerService;
    @BeforeEach
    public void setUp(){
        mockOffersRepo = Mockito.mock(OfferRepo.class);
        offerService = new OfferService(mockOffersRepo);
    }

    @Test
    public void whenFindAllOffersInvoked_ThenShouldReturnTwoElements() {
        //given
        final List<OfferDto> expectedAllOffers = Arrays.asList(cybersourceOffer(), cdqPolandOffer());
        when(mockOffersRepo.findAll()).thenReturn(Arrays.asList(cybersourceOffer, cdqPolandOffer));

        //when
        final List<OfferDto> actualAllOffers = offerService.findAllOffers();

        BDDAssertions.then(actualAllOffers).containsExactlyInAnyOrderElementsOf(expectedAllOffers);
        BDDAssertions.then(actualAllOffers.toArray(new OfferDto[0])).isInstanceOf(OfferDto[].class);

    }

    @Test
    public void whenFindOfferWithIdInvoked_ThenShouldReturnOneElement() {

        //given
        when(mockOffersRepo.findById(MongoOffersIDs.cdqPoland)).thenReturn(Optional.of(cdqPolandOffer));

        //when
        final OfferDto offerById = offerService.findOfferById(MongoOffersIDs.cdqPoland);

        //then
        BDDAssertions.then(offerById).isEqualToComparingOnlyGivenFields(cdqPolandOffer);
    }

    @Test
    public void whenFindOfferWithIdInvoked_ThenShouldReturnOneDifferentElement() {

        //given
        when(mockOffersRepo.findById(MongoOffersIDs.cybersource)).thenReturn(Optional.of(cybersourceOffer));

        //when
        final OfferDto offerById = offerService.findOfferById(MongoOffersIDs.cybersource);

        //then
        BDDAssertions.then(offerById).isEqualToComparingOnlyGivenFields(cybersourceOffer);
    }

    @Test
    public void whenFindOfferWithNotExistingIdInvoked_ThenShouldThrowOfferNotFoundException() {

        //when
        //then
        Assertions.assertThatThrownBy(() -> offerService.findOfferById("100"))
                .isInstanceOf(OfferNotFoundException.class)
                        .hasMessage("Offer with id of 100 was not found!");
    }

}