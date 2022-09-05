package pl.tomaszborowski.junior_jobs.offer.domain.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

