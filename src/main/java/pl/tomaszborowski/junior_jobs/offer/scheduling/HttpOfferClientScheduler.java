package pl.tomaszborowski.junior_jobs.offer.scheduling;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.tomaszborowski.junior_jobs.infrastructure.RemoteOfferClient;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
class HttpOfferClientScheduler {

    private final OfferService offerService;
    private final RemoteOfferClient remoteOfferClient;

    private static final Logger logger = LoggerFactory.getLogger(HttpOfferClientScheduler.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final String STARTED_OFFERS_FETCHING_MESSAGE = "Started offers fetching {}";
    private static final String STOPPED_OFFERS_FETCHING_MESSAGE = "Stopped offers fetching {}";
    private static final String ADDED_NEW_OFFERS_MESSAGE = "Added new {} offers";

    @Scheduled(fixedDelayString = "${http.offers.scheduler.request.delay}")
    public void getOffersFromHttpClientEvery3Hours() {
        logger.info(STARTED_OFFERS_FETCHING_MESSAGE, dateFormat.format(new Date()));
        List<pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDto> offersFromHttpClient = remoteOfferClient.getOffers();
        List<OfferDto> offersAddedToDB = offerService.saveOffersDirectlyFromHttpClient(offersFromHttpClient);
        logger.info(ADDED_NEW_OFFERS_MESSAGE, offersAddedToDB.size());
        logger.info(STOPPED_OFFERS_FETCHING_MESSAGE, dateFormat.format(new Date()));

    }


}
