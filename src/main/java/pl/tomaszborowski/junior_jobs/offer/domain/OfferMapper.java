package pl.tomaszborowski.junior_jobs.offer.domain;

import pl.tomaszborowski.junior_jobs.offer.domain.Dao.Offer;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;

public class OfferMapper {
    public static OfferDto mapOfferToDto(Offer offer){
        return OfferDto.builder()
                .id(offer.getId())
                .companyName(offer.getCompany())
                .position(offer.getPosition())
                .salary(offer.getSalary())
                .offerUrl(offer.getSalary())
                .build();
    }

    public static Offer mapToOffer(OfferDto offerDto){
        return Offer.builder()
                .id(offerDto.getId())
                .company(offerDto.getCompanyName())
                .position(offerDto.getPosition())
                .salary(offerDto.getSalary())
                .offerUrl(offerDto.getOfferUrl())
                .build();
    }
}
