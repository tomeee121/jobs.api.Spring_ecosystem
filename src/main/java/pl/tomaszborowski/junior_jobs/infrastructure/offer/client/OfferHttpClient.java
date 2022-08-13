package pl.tomaszborowski.junior_jobs.infrastructure.offer.client;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import pl.tomaszborowski.junior_jobs.infrastructure.RemoteOfferClient;
import pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDTO;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
public class OfferHttpClient implements RemoteOfferClient {

    private final RestTemplate restTemplate;
    private final String Uri;

    @Override
    public List<OfferDTO> getOffers() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity httpEntity = new HttpEntity(httpHeaders);
        try{
            ResponseEntity<List<OfferDTO>> offersDTOResponse = restTemplate.exchange(Uri, HttpMethod.GET, httpEntity,
                    new ParameterizedTypeReference<List<OfferDTO>>() {});
            final List<OfferDTO> offersDTO = offersDTOResponse.getBody();
            return offersDTO != null ? offersDTO : Collections.emptyList();
        }
        catch (ResourceAccessException e){
            return Collections.emptyList();
        }

    }
}
