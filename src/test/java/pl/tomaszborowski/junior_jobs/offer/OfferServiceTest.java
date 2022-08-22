package pl.tomaszborowski.junior_jobs.offer;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import pl.tomaszborowski.junior_jobs.offer.Dto.OfferDtoSamples;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferService;

import java.util.Arrays;
import java.util.List;

class OfferServiceTest implements OfferDtoSamples {

    @Test
    public void whenFindAllOffersInvoked_ThenShouldReturnTwoElements() {
        //given
        OfferService offerService = new OfferService();
        final List<OfferDto> expectedAllOffers = Arrays.asList(cybersourceOffer(), cdqPolandOffer());

        //when
        final List<OfferDto> actualAllOffers = offerService.findAllOffers();

        BDDAssertions.then(actualAllOffers).containsExactlyInAnyOrderElementsOf(expectedAllOffers);
        BDDAssertions.then(actualAllOffers.toArray(new OfferDto[0])).isInstanceOf(OfferDto[].class);

    }

    @Test
    public void whenFindOfferWithIdInvoked_ThenShouldReturnOneElement() {
        //given
        OfferService offerService = new OfferService();

        //when
        final OfferDto offerById = offerService.findOfferById(2L);

        //then
        BDDAssertions.then(offerById).isEqualTo(cdqPolandOffer());
    }

    @Test
    public void whenFindOfferWithIdInvoked_ThenShouldReturnOneDifferentElement() {
        //given
        OfferService offerService = new OfferService();

        //when
        final OfferDto offerById = offerService.findOfferById(1L);

        //then
        BDDAssertions.then(offerById).isEqualTo(cybersourceOffer());
    }

    @Test
    public void whenFindOfferWithNotExistingIdInvoked_ThenShouldThrowOfferNotFoundException() {
        //given
        OfferService offerService = new OfferService();

        //when
        //then
        Assertions.assertThatThrownBy(() -> offerService.findOfferById(100)).isInstanceOf(OfferNotFoundException.class)
                        .hasMessage("Offer with id of 100 was not found!");
    }

}