package pl.tomaszborowski.junior_jobs.infrastructure;

import pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDTO;

import java.util.List;

public interface RemoteOfferClient {
    List<OfferDTO> getOffers();
}
