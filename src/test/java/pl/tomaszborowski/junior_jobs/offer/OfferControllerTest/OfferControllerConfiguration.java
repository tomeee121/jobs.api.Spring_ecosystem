package pl.tomaszborowski.junior_jobs.offer.OfferControllerTest;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tomaszborowski.junior_jobs.config.MongoOffersIDs;
import pl.tomaszborowski.junior_jobs.offer.OfferController;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferControllerExceptionHandler;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferDtoSamples;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferRepo;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferService;

import java.util.Arrays;
import java.util.List;

@Configuration(proxyBeanMethods = false)
class OfferControllerConfiguration implements OfferDtoSamples {

    @Bean
    OfferControllerExceptionHandler offerControllerExceptionHandler(){
        return new OfferControllerExceptionHandler();
    }

    @Bean
    OfferService offerService(){

        OfferRepo offerRepoMock = Mockito.mock(OfferRepo.class);
        return new OfferService(offerRepoMock){
            @Override
            public List<OfferDto> findAllOffers() {
                return Arrays.asList(cybersourceDtoOffer(), cdqPolandDtoOffer());
            }

            @Override
            public OfferDto findOfferById(String id) {
                if(id.equals(MongoOffersIDs.cybersource)){
                    return cybersourceDtoOffer();
                }
                else if(id.equals(MongoOffersIDs.cdqPoland)){
                    return cdqPolandDtoOffer();
                }
                else {
                    throw new OfferNotFoundException(id);
                }
            }
        };

    }
    @Bean
    OfferController offerController(OfferService offerService){
        return new OfferController(offerService);
    }
}