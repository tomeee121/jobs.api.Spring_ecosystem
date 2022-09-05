package pl.tomaszborowski.junior_jobs.offer.domain;

import pl.tomaszborowski.junior_jobs.offer.domain.Dao.Offer;
import pl.tomaszborowski.junior_jobs.offer.domain.Dto.OfferDto;

public interface OfferSamples {

    default Offer cybersourceOffer(){
        return cybersourceOffer;
    }

    default Offer cdqPolandOffer(){
        return cdqPolandOffer;
    }

    default Offer createOffer(String companyName, String position,String salary, String offerUrl){
        Offer offer = new Offer();
        offer.setCompany(companyName);
        offer.setPosition(position);
        offer.setSalary(salary);
        offer.setOfferUrl(offerUrl);
        return offer;
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
