package pl.tomaszborowski.junior_jobs.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferControllerExceptionHandler;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferService;

@Configuration(proxyBeanMethods = false)
public class OfferControllerConfig {

    @Bean
    OfferControllerExceptionHandler offerControllerExceptionHandler(){
        return new OfferControllerExceptionHandler();
    }

    @Bean
    OfferService offerService(){
        return new OfferService();
    }
    @Bean
    OfferController offerController(OfferService offerService){
        return new OfferController(offerService);
    }
}
