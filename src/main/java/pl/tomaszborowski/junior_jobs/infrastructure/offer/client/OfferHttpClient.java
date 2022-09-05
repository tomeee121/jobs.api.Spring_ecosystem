package pl.tomaszborowski.junior_jobs.infrastructure.offer.client;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import pl.tomaszborowski.junior_jobs.infrastructure.RemoteOfferClient;
import pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDto;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class OfferHttpClient implements RemoteOfferClient {

    private final RestTemplate restTemplate;
    private final String Uri;


    @Override
    public List<OfferDto> getOffers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity httpEntity = new HttpEntity(httpHeaders);
        try{
            ResponseEntity<List<OfferDto>> offersDTOResponse = restTemplate.exchange(Uri, HttpMethod.GET, httpEntity,
                    new ParameterizedTypeReference<List<OfferDto>>() {});
            final List<OfferDto> offersDTO = offersDTOResponse.getBody();
            return offersDTO != null ? offersDTO : Collections.emptyList();
        }
        catch (ResourceAccessException e){
            return Collections.emptyList();
        }

    }

}
