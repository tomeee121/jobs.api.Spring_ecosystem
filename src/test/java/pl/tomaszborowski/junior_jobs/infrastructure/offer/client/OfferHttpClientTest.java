package pl.tomaszborowski.junior_jobs.infrastructure.offer.client;


import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class OfferHttpClientTest implements RestTemplateExchange{

    @Test
    void shouldReturnOneElementListOfOffers_whenCalledExternalApi(){
        //given
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        String uri = "test";

        when(getExchange(restTemplate))
                .thenReturn(getEmptyResponseEntityWith());
        OfferHttpClient offerHttpClient = new OfferHttpClient(restTemplate, uri);

        //when
        List<OfferDTO> offers = offerHttpClient.getOffers();

        //then
        Assertions.assertThat(offers.size()).isEqualTo(0);
    }

    @Test
    void shouldReturnEmptyListOfOffers_whenCalledExternalApi(){
        //given
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        String uri = "test";

        when(getExchange(restTemplate))
                .thenReturn(getResponseEntityWithOneElement());
        OfferHttpClient offerHttpClient = new OfferHttpClient(restTemplate, uri);

        //when
        List<OfferDTO> offers = offerHttpClient.getOffers();

        //then
        Assertions.assertThat(offers.size()).isEqualTo(1);
    }

    @Test
    void shouldReturnTwoElementsListOfOffers_whenCalledExternalApi(){
        //given
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        String uri = "test";

        when(getExchange(restTemplate))
                .thenReturn(getResponseEntityWithTwoElements());
        OfferHttpClient offerHttpClient = new OfferHttpClient(restTemplate, uri);

        //when
        List<OfferDTO> offers = offerHttpClient.getOffers();

        //then
        Assertions.assertThat(offers.size()).isEqualTo(2);
    }


}