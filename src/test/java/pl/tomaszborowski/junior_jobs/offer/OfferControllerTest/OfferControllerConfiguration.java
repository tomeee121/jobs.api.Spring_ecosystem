package pl.tomaszborowski.junior_jobs.offer.OfferControllerTest;

import com.mongodb.DuplicateKeyException;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import pl.tomaszborowski.junior_jobs.config.MessageSourceConfig;
import pl.tomaszborowski.junior_jobs.config.MongoOffersIDs;
import pl.tomaszborowski.junior_jobs.offer.OfferController;
import pl.tomaszborowski.junior_jobs.offer.domain.Dao.Offer;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferControllerExceptionHandler;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferExistsException;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferDtoSamples;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferMapper;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferRepo;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferService;
import pl.tomaszborowski.junior_jobs.security.SecurityConfig;

import java.util.Arrays;
import java.util.List;

@Import({JwtFilterTestConfig.class, SecurityConfig.class, MessageSourceConfig.class})
public class OfferControllerConfiguration implements OfferDtoSamples {

    @Bean
    OfferControllerExceptionHandler offerControllerExceptionHandler(){
        return new OfferControllerExceptionHandler();
    }

    @Bean
    OfferService offerService() {

        OfferRepo offerRepoMock = Mockito.mock(OfferRepo.class);
        return new OfferService(offerRepoMock) {
            @Override
            public List<OfferDto> findAllOffers() {
                return Arrays.asList(cybersourceDtoOffer(), cdqPolandDtoOffer());
            }

            @Override
            public OfferDto findOfferById(String id) {
                if (id.equals(MongoOffersIDs.cybersource)) {
                    return cybersourceDtoOffer();
                } else if (id.equals(MongoOffersIDs.cdqPoland)) {
                    return cdqPolandDtoOffer();
                } else {
                    throw new OfferNotFoundException(id);
                }
            }

            @Override
            public OfferDto createOrUpdateOffer(OfferDto offerDto) {
                if (offerDto.equals(getExistingUrlDtoOffer())) {
                    throw new OfferExistsException("There is already an offer with URL of " + offerDto.getOfferUrl());
                }
                if (offerDto.getId() != null) {
                    return offerDto;
                }
                return getUniqueOfferDtoWithoutId();
            }
        };}


        @Bean
        OfferController offerController (OfferService offerService){
            return new OfferController(offerService);
        }
    }
