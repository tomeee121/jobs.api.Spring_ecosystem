package pl.tomaszborowski.junior_jobs.infrastructure;

import pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDto;

import java.util.List;

public interface RemoteOfferClient {
    List<OfferDto> getOffers();
}
