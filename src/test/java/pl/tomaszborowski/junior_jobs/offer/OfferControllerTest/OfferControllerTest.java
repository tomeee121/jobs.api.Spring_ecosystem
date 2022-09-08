package pl.tomaszborowski.junior_jobs.offer.OfferControllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.Exceptions.OfferErrorResponse;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferDtoSamples;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = OfferControllerConfiguration.class)
class OfferControllerTest implements OfferDtoSamples {

    private static final String AUTH_HEADER_VALUE = "Bearer JwtToken";
    private static final String AUTH_HEADER_NAME = "Authorization";

    @Test
    void shouldReturnStatusOkWithTwoElements_whenEndpointOffersInvoked(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) throws Exception {
        //given
        final List<OfferDto> offersList = Arrays.asList(cybersourceDtoOffer(), cdqPolandDtoOffer());
        String offers = objectMapper.writeValueAsString(offersList);

        //when
        final MvcResult mvcResult = mockMvc.perform(get("/offers").header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String actualResponseEntityBody = mvcResult.getResponse().getContentAsString();

        //then
        Assertions.assertThat(actualResponseEntityBody).isEqualTo(offers);

    }

    @Test
    public void shouldReturnStatusOkWithGivenElement_whenEndpointOffersWithPathVariableInvoked(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) throws Exception {
        //given
        String expectedResponseBody = objectMapper.writeValueAsString(cybersourceDtoOffer());

        //when
        final MvcResult mvcResult = mockMvc.perform(get("/offers/63073c6c2db2415cbc03afab").header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE))
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
        final MvcResult mvcResult = mockMvc.perform(get("/offers/99").header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE))
                .andExpect(status().isNotFound())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();

        //then
        assertThat(contentAsString).isEqualTo(offerErrorResponseJSON);
    }

    @Test
    public void whenNotExistingOfferAddedWithoutId_shouldReturnStatusCreated(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) throws Exception {
        //given
        String uniqueOfferWithoutIdToAdd = objectMapper.writeValueAsString(getUniqueOfferDtoWithoutId());

        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/offers").content(uniqueOfferWithoutIdToAdd)
                        .header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated()).andReturn();
        String contentAdded = mvcResult.getResponse().getContentAsString();

        //then
        Assertions.assertThat(contentAdded).isEqualTo(uniqueOfferWithoutIdToAdd);
    }

    @Test
    public void whenExistingOfferAddedWithId_shouldReturnStatusOK(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) throws Exception {
        //given
        String offerToBeUpdatedWithId = objectMapper.writeValueAsString(getUniqueOfferDtoWithId());

        //when
        MvcResult mvcResult = mockMvc.perform(post("/offers").header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE)
                        .content(offerToBeUpdatedWithId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
        String contentUpdated = mvcResult.getResponse().getContentAsString();
        //then
        Assertions.assertThat(contentUpdated).isEqualTo(offerToBeUpdatedWithId);
    }

    @Test
    public void whenOfferWithDuplicateOfUniqueFieldAdded_shouldReturnStatusConflict(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) throws Exception {
        //given
        String expectedMvcErrorResponse = "There is already an offer with URL of " + getExistingUrlDtoOffer().getOfferUrl();
        String duplicateOfferToBeAdded = objectMapper.writeValueAsString(getExistingUrlDtoOffer());

        //when
        MvcResult mvcResult = mockMvc.perform(post("/offers").header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE)
                        .content(duplicateOfferToBeAdded)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isConflict()).andReturn();
        String JSONResponse = mvcResult.getResponse().getContentAsString();
        OfferErrorResponse offerErrorResponse = objectMapper.readValue(JSONResponse, OfferErrorResponse.class);
        //then
        Assertions.assertThat(offerErrorResponse.getMessage()).isEqualTo(expectedMvcErrorResponse);
    }
    @Test
    public void whenOfferWithEmptyFieldsAddeded_shouldReturnStatusBadRequest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) throws Exception {
        //given
        String emptyOfferJSON = objectMapper.writeValueAsString(getEmptyOfferDto());
        String emptyOfferUrlValidationMessage = "Offer url cannot be empty.";

        //when
        MvcResult mvcResult = mockMvc.perform(post("/offers").content(emptyOfferJSON).contentType(MediaType.APPLICATION_JSON_VALUE)
                .header(AUTH_HEADER_NAME, AUTH_HEADER_VALUE))
                .andExpect(status().isBadRequest()).andReturn();
        int responseStatus = mvcResult.getResponse().getStatus();
        String errorResponse = mvcResult.getResponse().getContentAsString();
        //THEN
        Assertions.assertThat(responseStatus).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(errorResponse).contains(emptyOfferUrlValidationMessage);
    }



}
