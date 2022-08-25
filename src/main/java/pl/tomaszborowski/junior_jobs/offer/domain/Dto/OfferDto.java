package pl.tomaszborowski.junior_jobs.offer.domain.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class OfferDto {

    @JsonProperty("id")
    private final String id;
    @JsonProperty("company")
    private final String companyName;
    @JsonProperty("position")
    private final String position;
    @JsonProperty("salary")
    private final String salary;
    @JsonProperty("offerUrl")
    private final String offerUrl;
}
