package pl.tomaszborowski.junior_jobs.infrastructure.offer.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.tomaszborowski.junior_jobs.config.ConfigHttpOfferBeansForTests;
import pl.tomaszborowski.junior_jobs.infrastructure.RemoteOfferClient;
import pl.tomaszborowski.junior_jobs.infrastructure.error.RestTemplateResponseErrorHandler;
import pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.util.SocketUtils.findAvailableTcpPort;

public class OfferHttpClientIntegrationTest implements OfferApiResponses{

    WireMockServer wireMockServer;
    int port = findAvailableTcpPort();

    RemoteOfferClient offerHttpClient;

    @BeforeEach
    void setUp(){
        wireMockServer = new WireMockServer(options().port(port));
        WireMock.configureFor(port);
        wireMockServer.start();
    }

    @AfterEach
    void tearDown(){
        wireMockServer.stop();
    }

    RemoteOfferClient remoteOfferClient = new ConfigHttpOfferBeansForTests().getOfferHttpClient(1000, 1000, "http://localhost:"+port+"/offers");
    RestTemplateResponseErrorHandler restTemplateResponseErrorHandler = new ConfigHttpOfferBeansForTests().restTemplateResponseErrorHandler();
    RestTemplate restTemplate = new ConfigHttpOfferBeansForTests().restTemplate(1000,1000, restTemplateResponseErrorHandler);


    @Test
    void whenCalledForOffersApiEndpoint_thenShouldReturnTwoElements() throws JSONException, JsonProcessingException {
        //given
        stubFor(WireMock.get(urlEqualTo("/offers"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getJSONOfTwoOffers())));
        //when
        //then
        then(remoteOfferClient.getOffers()).containsExactlyInAnyOrderElementsOf(Arrays.asList(getJuniorJavadeveloper(), getJuniorJavaFullstackdeveloper()));
    }

    @Test
    void whenCalledForOffersApiEndpoint_thenShouldReturnListOfSizeOne() throws JSONException, JsonProcessingException {
        //given
        stubFor(WireMock.get(urlEqualTo("/offers"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getJSONOfOneOffer())));
        //when
        //then
        then(remoteOfferClient.getOffers().size()).isEqualTo(1);
    }

    @Test
    void whenCalledForEmptyOffersApiEndpoint_thenShouldReturnEmptyCollection() throws JSONException, JsonProcessingException {
        //given
        stubFor(WireMock.get(urlEqualTo("/offers"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getEmptyCollectionJSON())));
        //when
        //then
        then(remoteOfferClient.getOffers().size()).isEqualTo(0);
    }

    @Test
    void whenCalledForOffersApiEndpointWithTwoElements_thenShouldReturnInstanceOfListOfDTOOffers() throws JSONException, JsonProcessingException {
        //given
        stubFor(WireMock.get(urlEqualTo("/offers"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(getJSONOfTwoOffers())));
        //when
        //then
        List<OfferDTO> offerDTOList = new ArrayList<>();
        then(remoteOfferClient.getOffers()).isInstanceOf(offerDTOList.getClass());
    }


    @Test
    void whenCalledForExistingOffersApiEndpoint_thenShouldReceiveStatus200() throws JSONException, JsonProcessingException {
        //given
        stubFor(WireMock.get(urlEqualTo("/offers"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody(getJSONOfTwoOffers())));
        //when
        ResponseEntity<List<OfferDTO>> exchange = restTemplate.exchange("http://localhost:" + port + "/offers", HttpMethod.GET, ArgumentMatchers.any(),
                new ParameterizedTypeReference<List<OfferDTO>>() {});

        //then
        Assertions.assertEquals(200, exchange.getStatusCodeValue());
    }

}
