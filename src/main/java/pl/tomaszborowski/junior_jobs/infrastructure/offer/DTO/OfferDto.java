package pl.tomaszborowski.junior_jobs.infrastructure.offer.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class OfferDto {
    private String title;
    private String company;
    private String salary;
    private String offerUrl;
}
