package pl.tomaszborowski.junior_jobs.offer.domain.Exceptions;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class OfferErrorResponse {

    private final String message;
    private final HttpStatus httpStatus;

}
