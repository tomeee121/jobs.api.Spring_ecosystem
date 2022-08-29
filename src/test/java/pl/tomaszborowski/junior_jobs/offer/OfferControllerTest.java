package pl.tomaszborowski.junior_jobs.offer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.tomaszborowski.junior_jobs.config.OfferControllerConfig;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDtoSamples;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferErrorResponse;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = OfferControllerConfig.class)
class OfferControllerTest implements OfferDtoSamples {

    @Test
    void shouldReturnStatusOkWithTwoElements_whenEndpointOffersInvoked(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) throws Exception {
        //given
        final List<OfferDto> offersList = Arrays.asList(cybersourceOffer(), cdqPolandOffer());
        String offers = objectMapper.writeValueAsString(offersList);

        //when
        final MvcResult mvcResult = mockMvc.perform(get("/offers"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String actualResponseEntityBody = mvcResult.getResponse().getContentAsString();

        //then
        Assertions.assertThat(actualResponseEntityBody).isEqualTo(offers);

    }

    @Test
    public void shouldReturnStatusOkWithGivenElement_whenEndpointOffersWithPathVariableInvoked(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) throws Exception {
        //given
        String expectedResponseBody = objectMapper.writeValueAsString(cybersourceOffer());

        //when
        final MvcResult mvcResult = mockMvc.perform(get("/offers/63073c6c2db2415cbc03afab"))
                .andExpect(status().isOk())
                .andReturn();
        String actualResponseEntityBody = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(actualResponseEntityBody).isEqualTo(expectedResponseBody);
    }

    @Test
    public void shouldThrowOfferNotFoundException_whenEndpointOffersWithNonExistingPathVariableInvoked(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) throws Exception {
        //given
        OfferErrorResponse offerErrorResponse = new OfferErrorResponse("Offer with id of 99 was not found!", HttpStatus.NOT_FOUND);
        String offerErrorResponseJSON = objectMapper.writeValueAsString(offerErrorResponse);

        //when
        final MvcResult mvcResult = mockMvc.perform(get("/offers/99"))
                .andExpect(status().isNotFound())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(contentAsString).isEqualTo(offerErrorResponseJSON);
    }

}