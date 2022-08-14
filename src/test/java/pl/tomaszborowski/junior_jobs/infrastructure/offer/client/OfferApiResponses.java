package pl.tomaszborowski.junior_jobs.infrastructure.offer.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO.OfferDTO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface OfferApiResponses {
    default String getJSONOfTwoOffers() throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        OfferDTO offerDTO = new OfferDTO("Junior Java developer", "Softserve",
                "5,5k - 7k", "https://softserve.com/example1");
        OfferDTO offerDTO1 = new OfferDTO("Junior fullstack Java developer", "Capgemini",
                "6,5k - 8k", "https://capgemini.com/example1");
        String offers = objectMapper.writeValueAsString(Arrays.asList(offerDTO, offerDTO1));
        // "[{\"title\":\"Junior Java developer\",\"company\":\"Softserve\",\"salary\":\"5,5k - 7k\",\"offerUrl\":\"https://softserve.com/example1\"},{\"title\":\"Junior fullstack Java developer\",\"company\":\"Capgemini\",\"salary\":\"6,5k - 8k\",\"offerUrl\":\"https://capgemini.com/example1\"}]"
        return offers;
    }
    default String getJSONOfOneOffer() throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        OfferDTO offerDTO = new OfferDTO("Junior Java developer", "Softserve",
                "5,5k - 7k", "https://softserve.com/example1");

        List<OfferDTO> offerDTOList = Arrays.asList(offerDTO);
        String offers = objectMapper.writeValueAsString(offerDTOList);
        // "[{\"title\":\"Junior Java developer\",\"company\":\"Softserve\",\"salary\":\"5,5k - 7k\",\"offerUrl\":\"https://softserve.com/example1\"}]"
        return offers;
    }
    default String getEmptyCollectionJSON() throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String offers = objectMapper.writeValueAsString(Collections.emptyList());
        return offers;
    }

    default OfferDTO getJuniorJavadeveloper(){
        return new OfferDTO("Junior Java developer", "Softserve",
                "5,5k - 7k", "https://softserve.com/example1");
    }

    default OfferDTO getJuniorJavaFullstackdeveloper(){
        return new OfferDTO("Junior fullstack Java developer", "Capgemini",
                "6,5k - 8k", "https://capgemini.com/example1");
    }
}
