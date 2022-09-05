package pl.tomaszborowski.junior_jobs.offer.domain.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class OfferDto {

    @JsonProperty("id")
    private String id;
    @JsonProperty("company")
    @NotNull(message = "Offer company name cannot be null.")
    @NotEmpty(message = "Offer company name cannot be empty.")
    private String companyName;
    @JsonProperty("position")
    @NotNull(message = "Offer position cannot be null.")
    @NotEmpty(message = "Offer position cannot be empty.")
    private String position;
    @JsonProperty("salary")
    @NotNull(message = "Offer salary cannot be null.")
    @NotEmpty(message = "Offer salary cannot be empty.")
    private String salary;
    @JsonProperty("offerUrl")
    @NotNull(message = "Offer url cannot be null.")
    @NotEmpty(message = "Offer url cannot be empty.")
    private String offerUrl;
}

