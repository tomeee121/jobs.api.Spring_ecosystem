package pl.tomaszborowski.junior_jobs.offer.Dto;

import pl.tomaszborowski.junior_jobs.offer.domain.Dao.Offer;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;
import pl.tomaszborowski.junior_jobs.offer.domain.OfferMapper;

public interface OfferDtoSamples {
    default OfferDto cybersourceOffer(){
        return OfferMapper.mapOfferToDto(cybersourceOffer);
    }

    default OfferDto cdqPolandOffer(){
        return OfferMapper.mapOfferToDto(cdqPolandOffer);
    }


    Offer cybersourceOffer = new Offer("63073c6c2db2415cbc03afab",
            "Software Engineer - Mobile (m/f/d)",
            "4k - 8k PLN",
            "https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn",
            "Cybersource");

    Offer cdqPolandOffer = new Offer("63073c6c2db2415cbc03afac",
            "Junior DevOps Engineer",
            "8k - 14k PLN",
            "https://nofluffjobs.com/pl/job/junior-devops-engineer-cdq-poland-wroclaw-gnymtxqd",
            "CDQ Poland");

}