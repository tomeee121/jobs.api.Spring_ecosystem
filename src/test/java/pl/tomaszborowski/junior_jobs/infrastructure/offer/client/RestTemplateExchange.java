package pl.tomaszborowski.junior_jobs.infrastructure.offer.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDto;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public interface RestTemplateExchange {
    default ResponseEntity<Object> getExchange(RestTemplate restTemplate) {
        return restTemplate.exchange(anyString(), any(HttpMethod.class), any(), (ParameterizedTypeReference<Object>) any());
    }

    default ResponseEntity<Object> getResponseEntityWithTwoElements() {
        return new ResponseEntity<>(Arrays.asList(new OfferDto("a", "b", "c", "d"),
                new OfferDto("a2", "b2", "c2", "d2")),
                HttpStatus.ACCEPTED);
    }
    default ResponseEntity<Object> getResponseEntityWithOneElement() {
        return new ResponseEntity<>(Collections.singletonList(new OfferDto("a", "b", "c", "d")),
                HttpStatus.ACCEPTED);
    }
    default ResponseEntity<Object> getEmptyResponseEntityWith() {
        return new ResponseEntity<>(Collections.emptyList(),
                HttpStatus.ACCEPTED);
    }
}
