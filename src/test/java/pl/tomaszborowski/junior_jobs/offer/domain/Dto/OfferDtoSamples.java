package pl.tomaszborowski.junior_jobs.offer.domain.Dto;

import pl.tomaszborowski.junior_jobs.offer.domain.OfferMapper;

public interface OfferDtoSamples {
    default OfferDto cybersourceOffer(){
        return OfferMapper.mapOfferToDto("Software Engineer - Mobile (m/f/d)",
                "4k - 8k PLN",
                "https://nofluffjobs.com/pl/job/software-engineer-mobile-m-f-d-cybersource-poznan-entavdpn",
                "Cybersource");
    }

    default OfferDto cdqPolandOffer(){
        return OfferMapper.mapOfferToDto("Junior DevOps Engineer",
                "8k - 14k PLN",
                "https://nofluffjobs.com/pl/job/junior-devops-engineer-cdq-poland-wroclaw-gnymtxqd",
                "CDQ Poland");
    }



}