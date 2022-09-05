package pl.tomaszborowski.junior_jobs.offer.domain.Exceptions;

public class OfferExistsException extends RuntimeException{
    public OfferExistsException(String message) {
        super(message);
    }
}
