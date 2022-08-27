package pl.tomaszborowski.junior_jobs.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tomaszborowski.junior_jobs.offer.Dto.OfferDtoSamples;
import pl.tomaszborowski.junior_jobs.offer.OfferController;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferControllerExceptionHandler;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferNotFoundException;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferRepo;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@Configuration(proxyBeanMethods = false)
public class OfferControllerConfig implements OfferDtoSamples {

    @Bean
    OfferControllerExceptionHandler offerControllerExceptionHandler(){
        return new OfferControllerExceptionHandler();
    }

    @Bean
    public OfferService offerService(){

        OfferRepo offerRepoMock = Mockito.mock(OfferRepo.class);
        return new OfferService(offerRepoMock){
            @Override
            public List<OfferDto> findAllOffers() {
                return Arrays.asList(cybersourceOffer(), cdqPolandOffer());
            }

            @Override
            public OfferDto findOfferById(String id) {
                if(id.equals(MongoOffersIDs.cybersource)){
                    return cybersourceOffer();
                }
                else if(id.equals(MongoOffersIDs.cdqPoland)){
                    return cdqPolandOffer();
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
