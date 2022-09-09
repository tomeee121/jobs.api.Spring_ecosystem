package pl.tomaszborowski.junior_jobs.offer.domain.Exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

@ControllerAdvice
@Slf4j
public class OfferControllerExceptionHandler {
    @ExceptionHandler(OfferNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<OfferErrorResponse> offerErrorResponse(OfferNotFoundException exception){
        OfferErrorResponse offerErrorResponse = new OfferErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
        log.info("Exception thrown with message: "+offerErrorResponse);

        return new ResponseEntity<>(offerErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OfferExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<OfferErrorResponse> offerExistsExceptionResponse(OfferExistsException exception){
        OfferErrorResponse offerErrorResponse = new OfferErrorResponse(exception.getMessage(), HttpStatus.CONFLICT);
        log.info("Exception thrown with message: "+offerErrorResponse);

        return new ResponseEntity<>(offerErrorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<OfferErrorResponse> constraintViolationExceptionResponse(MethodArgumentNotValidException exception){
        ArrayList<String> exceptions = new ArrayList<>();
        exception.getBindingResult().getAllErrors().forEach(exceptionOccured -> exceptions.add(exceptionOccured.getDefaultMessage().toString()));
        OfferErrorResponse offerErrorResponse = new OfferErrorResponse(exceptions.toString(), HttpStatus.BAD_REQUEST);
        log.info("Exception thrown with message: "+offerErrorResponse);

        return new ResponseEntity<>(offerErrorResponse, HttpStatus.BAD_REQUEST);
    }

}

