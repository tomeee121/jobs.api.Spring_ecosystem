package pl.tomaszborowski.junior_jobs.offer.domain.Exceptions;

import lombok.Getter;

@Getter
public class OfferNotFoundException extends RuntimeException{
    private final String OfferId;

    public OfferNotFoundException(String offerId) {
        super(!offerId.equals("-1") ? String.format("Offer with id of %s was not found!", offerId) : "There was no offers!");
        OfferId = offerId;
    }
}
