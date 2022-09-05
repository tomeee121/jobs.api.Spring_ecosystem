package pl.tomaszborowski.junior_jobs.offer.domain;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import pl.tomaszborowski.junior_jobs.config.MongoOffersIDs;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferExistsException;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
        final List<OfferDto> expectedAllOffers = Arrays.asList(cybersourceDtoOffer(), cdqPolandDtoOffer());
        when(mockOffersRepo.findAll()).thenReturn(Arrays.asList(OfferDtoSamples.cybersourceOffer, OfferDtoSamples.cdqPolandOffer));

        //when
        final List<OfferDto> actualAllOffers = offerService.findAllOffers();

        BDDAssertions.then(actualAllOffers).containsExactlyInAnyOrderElementsOf(expectedAllOffers);
        BDDAssertions.then(actualAllOffers.toArray(new OfferDto[0])).isInstanceOf(OfferDto[].class);

    }

    @Test
    public void whenFindOfferWithIdInvoked_ThenShouldReturnOneElement() {

        //given
        when(mockOffersRepo.findById(MongoOffersIDs.cdqPoland)).thenReturn(Optional.of(OfferDtoSamples.cdqPolandOffer));

        //when
        final OfferDto offerById = offerService.findOfferById(MongoOffersIDs.cdqPoland);

        //then
        BDDAssertions.then(offerById).isEqualToComparingOnlyGivenFields(OfferDtoSamples.cdqPolandOffer);
    }

    @Test
    public void whenFindOfferWithIdInvoked_ThenShouldReturnOneDifferentElement() {

        //given
        when(mockOffersRepo.findById(MongoOffersIDs.cybersource)).thenReturn(Optional.of(OfferDtoSamples.cybersourceOffer));

        //when
        final OfferDto offerById = offerService.findOfferById(MongoOffersIDs.cybersource);

        //then
        BDDAssertions.then(offerById).isEqualToComparingOnlyGivenFields(OfferDtoSamples.cybersourceOffer);
    }

    @Test
    public void whenFindOfferWithNotExistingIdInvoked_ThenShouldThrowOfferNotFoundException() {

        //when
        //then
        Assertions.assertThatThrownBy(() -> offerService.findOfferById("100"))
                .isInstanceOf(OfferNotFoundException.class)
                        .hasMessage("Offer with id of 100 was not found!");
    }

    @Test
    public void whenOfferDtoPassedToCreateOrUpdateMethod_thenShouldReturnTheObject() {
        //given
        when(mockOffersRepo.save((offerWithUniqueFieldsAndId))).thenReturn(offerWithUniqueFieldsAndId);

        //when
        OfferDto actual = offerService.createOrUpdateOffer(getUniqueOfferDtoWithId());

        //then
        assertThat(actual).isNotNull().hasNoNullFieldsOrProperties();
    }

    @Test
    public void whenOfferDtoWithSameUrlPassedToCreateOrUpdateMethod_thenShouldThrowOfferExistsException() {
        //given
        OfferDto existingUrlDtoOffer = getExistingUrlDtoOffer();

        //when
        given(offerService.createOrUpdateOffer(existingUrlDtoOffer)).willThrow(new OfferExistsException("copy"));

        //then
        assertThatThrownBy(() -> offerService.createOrUpdateOffer(existingUrlDtoOffer)).isInstanceOf(OfferExistsException.class);
    }

}