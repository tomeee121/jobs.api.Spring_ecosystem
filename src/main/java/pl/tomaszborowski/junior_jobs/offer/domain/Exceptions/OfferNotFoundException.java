package pl.tomaszborowski.junior_jobs.offer.domain.Exceptions;

import lombok.Getter;

@Getter
public class OfferNotFoundException extends RuntimeException{
    private final long OfferId;

    public OfferNotFoundException(long offerId) {
        super(offerId != -1 ? String.format("Offer with id of %d was not found!", offerId) : "There was no offers!");
        OfferId = offerId;
    }
}
