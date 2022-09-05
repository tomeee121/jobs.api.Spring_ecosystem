package pl.tomaszborowski.junior_jobs.offer.domain.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

@Builder
@Value
public class OfferDto {

    @JsonProperty("id")
    private String id;
    @JsonProperty("company")
    private String companyName;
    @JsonProperty("position")
    private String position;
    @JsonProperty("salary")
    private String salary;
    @JsonProperty("offerUrl")
    private String offerUrl;
}

