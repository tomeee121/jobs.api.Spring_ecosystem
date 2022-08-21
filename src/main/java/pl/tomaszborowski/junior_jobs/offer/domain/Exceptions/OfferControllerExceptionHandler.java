package pl.tomaszborowski.junior_jobs.offer.domain.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class OfferControllerExceptionHandler {
    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<OfferErrorResponse> offerErrorResponse(String message){
        OfferErrorResponse offerErrorResponse = new OfferErrorResponse(message, HttpStatus.NOT_FOUND);
        log.info("Exception thrown with message: "+offerErrorResponse);

        return new ResponseEntity<>(offerErrorResponse, HttpStatus.NOT_FOUND);
    }

}

