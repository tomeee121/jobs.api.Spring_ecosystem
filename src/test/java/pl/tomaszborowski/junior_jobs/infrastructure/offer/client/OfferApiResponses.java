package pl.tomaszborowski.junior_jobs.infrastructure.offer.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface OfferApiResponses {
    default String getJsonOfTwoOffers() throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        OfferDto offerDTO = new OfferDto("Junior Java developer", "Softserve",
                "5,5k - 7k", "https://softserve.com/example1");
        OfferDto offerDto1 = new OfferDto("Junior fullstack Java developer", "Capgemini",
                "6,5k - 8k", "https://capgemini.com/example1");
        String offers = objectMapper.writeValueAsString(Arrays.asList(offerDTO, offerDto1));
        // "[{\"title\":\"Junior Java developer\",\"company\":\"Softserve\",\"salary\":\"5,5k - 7k\",\"offerUrl\":\"https://softserve.com/example1\"},{\"title\":\"Junior fullstack Java developer\",\"company\":\"Capgemini\",\"salary\":\"6,5k - 8k\",\"offerUrl\":\"https://capgemini.com/example1\"}]"
        return offers;
    }
    default String getJSONOfOneOffer() throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        OfferDto offerDTO = new OfferDto("Junior Java developer", "Softserve",
                "5,5k - 7k", "https://softserve.com/example1");

        List<OfferDto> offerDtoList = Arrays.asList(offerDTO);
        String offers = objectMapper.writeValueAsString(offerDtoList);
        // "[{\"title\":\"Junior Java developer\",\"company\":\"Softserve\",\"salary\":\"5,5k - 7k\",\"offerUrl\":\"https://softserve.com/example1\"}]"
        return offers;
    }
    default String getEmptyCollectionJSON() throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String offers = objectMapper.writeValueAsString(Collections.emptyList());
        return offers;
    }

    default OfferDto getJuniorJavadeveloper(){
        return new OfferDto("Junior Java developer", "Softserve",
                "5,5k - 7k", "https://softserve.com/example1");
    }

    default OfferDto getJuniorJavaFullstackdeveloper(){
        return new OfferDto("Junior fullstack Java developer", "Capgemini",
                "6,5k - 8k", "https://capgemini.com/example1");
    }
}
