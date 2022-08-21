package pl.tomaszborowski.junior_jobs.offer.domain.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class OfferNotFoundException extends RuntimeException{
    private final long OfferId;

    public OfferNotFoundException(long offerId) {
        super(String.format("Offer with id of %d was not found!", offerId));
        OfferId = offerId;
    }
}
