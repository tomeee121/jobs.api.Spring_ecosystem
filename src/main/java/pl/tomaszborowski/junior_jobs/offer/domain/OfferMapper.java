package pl.tomaszborowski.junior_jobs.offer.domain;

import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;

public class OfferMapper {
    public static OfferDto mapOfferToDto(String companyName, String position, String salary, String offerUrl){
        return OfferDto.builder()
                .companyName(companyName)
                .position(position)
                .salary(salary)
                .offerUrl(offerUrl)
                .build();
    }
}
