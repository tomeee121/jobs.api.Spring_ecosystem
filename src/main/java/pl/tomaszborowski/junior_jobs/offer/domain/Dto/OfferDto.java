package pl.tomaszborowski.junior_jobs.offer.domain.Dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
public class OfferDto {

    private final String companyName;
    private final String position;
    private final String salary;
    private final String offerUrl;
}
